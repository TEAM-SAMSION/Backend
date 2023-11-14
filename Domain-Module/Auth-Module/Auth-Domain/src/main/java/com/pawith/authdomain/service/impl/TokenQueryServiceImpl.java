package com.pawith.authdomain.service.impl;

import com.pawith.authdomain.entity.Token;
import com.pawith.authdomain.exception.AuthError;
import com.pawith.authdomain.exception.NotExistTokenException;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.authdomain.repository.TokenRepository;
import com.pawith.authdomain.service.TokenQueryService;
import com.pawith.commonmodule.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class TokenQueryServiceImpl implements TokenQueryService {
    private final TokenRepository tokenRepository;
    @Override
    public String findEmailByValue(final String value, final TokenType tokenType) {
        Token token = findToken(value, tokenType);
        return token.getEmail();
    }

    @Override
    public Token findTokenByValue(String value, TokenType tokenType) {
        return findToken(value, tokenType);
    }

    public List<Token> findAllByEmailAndTokenType(String email, TokenType tokenType){
        return tokenRepository.findAllByEmailAndTokenType(email, tokenType);
    }

    private Token findToken(final String value, final TokenType tokenType){
        return tokenRepository.findByValueAndTokenType(value, tokenType)
            .orElseThrow(() -> new NotExistTokenException(AuthError.NOT_EXIST_TOKEN));
    }


}
