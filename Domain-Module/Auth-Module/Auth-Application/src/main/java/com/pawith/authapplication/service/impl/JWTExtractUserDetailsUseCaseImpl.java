package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.service.JWTExtractUserDetailsUseCase;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.commonmodule.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class JWTExtractUserDetailsUseCaseImpl implements JWTExtractUserDetailsUseCase<Long> {
    private final JWTProvider jwtProvider;

    @Override
    public Long extract(final String token) {
        return jwtProvider.extractUserClaimsFromToken(token, TokenType.ACCESS_TOKEN)
            .getUserId();
    }
}
