package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.service.JWTExtractEmailUseCase;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.commonmodule.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class JWTExtractEmailUseCaseImpl implements JWTExtractEmailUseCase {
    private final JWTProvider jwtProvider;

    @Override
    public String extractEmail(final String token){
        return jwtProvider.extractEmailFromAccessToken(token);
    }
}
