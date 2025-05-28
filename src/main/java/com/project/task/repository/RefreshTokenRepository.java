package com.project.task.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.task.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(Long userId);
}