package com.pawith.authdomain.service.impl;

import com.pawith.authdomain.entity.Token;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.authdomain.repository.TokenRepository;
import com.pawith.authdomain.service.TokenSaveService;
import com.pawith.commonmodule.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TokenSaveServiceImpl implements TokenSaveService {
    private final TokenRepository tokenRepository;

    @Override
    public void saveToken(final String token, final String email, final TokenType tokenType) {
        final Token saveToken = Token.builder()
            .tokenType(tokenType)
            .value(token)
            .email(email)
            .build();
        tokenRepository.save(saveToken);
    }

}
