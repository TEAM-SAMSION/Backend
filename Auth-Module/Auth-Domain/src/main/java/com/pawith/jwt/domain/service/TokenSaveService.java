package com.pawith.jwt.domain.service;

import com.pawith.jwt.jwt.TokenType;

public interface TokenSaveService {

    void saveToken(final String token, final String email, final TokenType tokenType);
}
