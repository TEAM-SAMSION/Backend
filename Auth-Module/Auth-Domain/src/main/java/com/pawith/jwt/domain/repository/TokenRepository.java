package com.pawith.jwt.domain.repository;

import com.pawith.jwt.domain.entity.Token;
import com.pawith.jwt.jwt.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndTokenType(String email, TokenType tokenType);;
}
