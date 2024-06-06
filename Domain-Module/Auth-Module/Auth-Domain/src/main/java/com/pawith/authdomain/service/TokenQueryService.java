package com.pawith.authdomain.service;

import com.pawith.authdomain.entity.Token;
import com.pawith.authdomain.jwt.TokenType;

public interface TokenQueryService {

    Token findTokenByValue(final String value, final TokenType tokenType);
}
