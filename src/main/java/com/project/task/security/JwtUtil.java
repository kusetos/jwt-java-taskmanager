package com.project.task.security;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access.expiration}")
    private long accessExpiration;
    @Value("${jwt.refresh.expiration}")
    @Getter
    private long refreshExpiration;

    public String generateToken(String subject, boolean refresh) {
        long exp = refresh ? refreshExpiration : accessExpiration;
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + exp))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String token) {
        try { Jwts.parser().setSigningKey(secret).parseClaimsJws(token); return true; }
        catch (JwtException | IllegalArgumentException e) { return false; }
    }
}