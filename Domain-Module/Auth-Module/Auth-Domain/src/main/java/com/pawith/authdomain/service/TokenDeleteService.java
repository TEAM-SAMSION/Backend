package com.pawith.authdomain.service;

import com.pawith.authdomain.entity.Token;

import java.util.List;

public interface TokenDeleteService {

    void deleteRefreshToken(final Token token);

    void deleteAllToken(final List<Token> tokenList);
}
