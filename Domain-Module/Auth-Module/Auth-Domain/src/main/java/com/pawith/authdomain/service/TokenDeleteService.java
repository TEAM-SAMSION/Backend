package com.pawith.authdomain.service;

import com.pawith.authdomain.entity.Token;

import java.util.List;

public interface TokenDeleteService {

    void deleteToken(final Token token);

    void deleteAllToken(final List<Token> tokenList);

    void deleteTokenByValue(final String value);
}
