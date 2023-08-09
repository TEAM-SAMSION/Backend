package com.petmory.authmodule.application.service;

import com.petmory.authmodule.application.port.in.JWTExtractEmailUseCase;
import com.petmory.jwt.JWTProvider;
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
