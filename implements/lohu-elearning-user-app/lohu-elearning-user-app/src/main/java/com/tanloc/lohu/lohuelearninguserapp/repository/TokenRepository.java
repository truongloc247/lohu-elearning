package com.tanloc.lohu.lohuelearninguserapp.repository;

import com.tanloc.lohu.lohuelearninguserapp.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByIdAndTokenAndExpirationTimeGreaterThanEqualAndIsVerifiedFalse(Long id, String token, LocalDateTime now);
    Optional<Token> findTopByEmailOrderByIdDesc(String email);
}
