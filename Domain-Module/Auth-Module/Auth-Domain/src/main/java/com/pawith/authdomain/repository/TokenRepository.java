package com.pawith.authdomain.repository;

import com.pawith.authdomain.entity.Token;
import com.pawith.authdomain.jwt.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndTokenType(String email, TokenType tokenType);

    List<Token> findAllByEmailAndTokenType(String email, TokenType tokenType);
}
