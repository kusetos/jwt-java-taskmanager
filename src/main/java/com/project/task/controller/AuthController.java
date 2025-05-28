package com.project.task.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.task.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private AuthService auth;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String,String> body) {
        return ResponseEntity.ok(auth.register(body.get("username"), body.get("password")));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        String access = auth.login(body.get("username"), body.get("password"));
        String refresh = auth.createRefreshToken(auth.getUserRepo().findByUsername(body.get("username")).get());
        return ResponseEntity.ok(Map.of("accessToken", access, "refreshToken", refresh));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String,String> body) {
        String access = auth.refreshToken(body.get("refreshToken"));
        return ResponseEntity.ok(Map.of("accessToken", access));
    }
}