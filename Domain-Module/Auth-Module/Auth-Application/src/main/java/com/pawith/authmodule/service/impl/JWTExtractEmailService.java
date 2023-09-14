package com.pawith.authmodule.service.impl;

import com.pawith.authmodule.service.JWTExtractEmailUseCase;
import com.pawith.authdomain.jwt.JWTProvider;
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
