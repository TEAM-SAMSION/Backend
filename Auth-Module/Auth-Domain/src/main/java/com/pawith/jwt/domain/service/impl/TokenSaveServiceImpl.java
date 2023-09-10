package com.pawith.jwt.domain.service.impl;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.jwt.domain.entity.Token;
import com.pawith.jwt.domain.repository.TokenRepository;
import com.pawith.jwt.domain.service.TokenSaveService;
import com.pawith.jwt.jwt.TokenType;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TokenSaveServiceImpl implements TokenSaveService {
    private final TokenRepository tokenRepository;

    @Override
    public void saveToken(final String token, final String email, final TokenType tokenType){
        tokenRepository.save(Token.createToken(tokenType, email, token));
    }

}
