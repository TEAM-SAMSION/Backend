package com.pawith.jwt.domain.service;

import com.pawith.jwt.jwt.TokenType;

public interface TokenQueryService {

    String findEmailByToken(final String value, final TokenType tokenType);
}
