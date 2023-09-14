package com.pawith.authdomain.service;

import com.pawith.authdomain.jwt.TokenType;

public interface TokenQueryService {

    String findEmailByToken(final String value, final TokenType tokenType);
}
