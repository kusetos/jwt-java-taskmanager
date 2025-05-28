package com.project.task.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.task.entity.RefreshToken;
import com.project.task.entity.User;
import com.project.task.repository.RefreshTokenRepository;
import com.project.task.repository.UserRepository;
import com.project.task.security.JwtUtil;

import lombok.Getter;

@Service
public class AuthService {
    @Autowired
    @Getter
    private UserRepository userRepo;
    @Autowired
    private RefreshTokenRepository tokenRepo;
    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User register(String username, String password) {
        User user = new User(); user.setUsername(username);
        user.setPassword(encoder.encode(password));
        return userRepo.save(user);
    }
    
    public String login(String username, String password) {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (!encoder.matches(password, user.getPassword())) throw new RuntimeException("Invalid credentials");
        return jwtUtil.generateToken(username, false);
    }

    public String createRefreshToken(User user) {
        tokenRepo.deleteByUserId(user.getId());
        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setToken(jwtUtil.generateToken(user.getUsername(), true));
        rt.setExpiryDate(Instant.now().plusMillis(jwtUtil.getRefreshExpiration()));
        return tokenRepo.save(rt).getToken();
    }

    public String refreshToken(String token) {
        RefreshToken rt = tokenRepo.findByToken(token)
            .filter(t -> t.getExpiryDate().isAfter(Instant.now()))
            .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        return jwtUtil.generateToken(rt.getUser().getUsername(), false);
    }

}