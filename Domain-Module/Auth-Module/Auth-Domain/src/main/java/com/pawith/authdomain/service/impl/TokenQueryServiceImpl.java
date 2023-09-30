package com.pawith.authdomain.service.impl;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.authdomain.entity.Token;
import com.pawith.authdomain.exception.NotExistTokenException;
import com.pawith.authdomain.repository.TokenRepository;
import com.pawith.authdomain.service.TokenQueryService;
import com.pawith.authdomain.jwt.TokenType;
import lombok.RequiredArgsConstructor;

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

    private Token findToken(final String value, final TokenType tokenType){
        return tokenRepository.findByValueAndTokenType(value, tokenType)
            .orElseThrow(() -> new NotExistTokenException(Error.NOT_EXIST_TOKEN));
    }


}
