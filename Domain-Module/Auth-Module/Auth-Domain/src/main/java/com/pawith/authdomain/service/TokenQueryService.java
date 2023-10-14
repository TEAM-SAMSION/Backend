package com.pawith.authdomain.service;

import com.pawith.authdomain.entity.Token;
import com.pawith.authdomain.jwt.TokenType;

import java.util.List;

public interface TokenQueryService {

    String findEmailByValue(final String value, final TokenType tokenType);

    Token findTokenByValue(final String value, final TokenType tokenType);

    List<Token> findAllByEmailAndTokenType(String email, TokenType tokenType);
}
