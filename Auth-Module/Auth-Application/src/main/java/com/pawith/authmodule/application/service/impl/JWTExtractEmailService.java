package com.pawith.authmodule.application.service.impl;

import com.pawith.authmodule.application.service.JWTExtractEmailUseCase;
import com.pawith.jwt.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JWTExtractEmailService implements JWTExtractEmailUseCase {
    private final JWTProvider jwtProvider;

    @Override
    public String extractEmail(final String token){
        return jwtProvider.extractEmailFromAccessToken(token);
    }
}
