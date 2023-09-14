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
    public String findEmailByToken(final String value, final TokenType tokenType) {
        Token token = tokenRepository.findByValueAndTokenType(value, tokenType)
            .orElseThrow(() -> new NotExistTokenException(Error.NOT_EXIST_TOKEN));
        return token.getEmail();
    }
}
