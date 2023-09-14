package com.pawith.authdomain.service;

import com.pawith.authdomain.jwt.TokenType;

public interface TokenSaveService {

    void saveToken(final String token, final String email, final TokenType tokenType);
}
