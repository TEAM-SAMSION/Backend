package com.pawith.authdomain.service;

import com.pawith.authdomain.exception.AuthError;
import com.pawith.authdomain.exception.NotExistTokenException;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.authdomain.repository.TokenRepository;
import com.pawith.commonmodule.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TokenValidateService {
    private final TokenRepository tokenRepository;

    public void validateIsExistToken(final String token, final TokenType tokenType) {
        if(!tokenRepository.existsByValueAndTokenType(token, tokenType)){
            throw new NotExistTokenException(AuthError.NOT_EXIST_TOKEN);
        }
    }
}
