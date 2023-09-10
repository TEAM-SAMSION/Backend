package com.pawith.jwt.domain.service.impl;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.jwt.domain.entity.Token;
import com.pawith.jwt.domain.exception.NotExistTokenException;
import com.pawith.jwt.domain.repository.TokenRepository;
import com.pawith.jwt.domain.service.TokenQueryService;
import com.pawith.jwt.jwt.TokenType;
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
