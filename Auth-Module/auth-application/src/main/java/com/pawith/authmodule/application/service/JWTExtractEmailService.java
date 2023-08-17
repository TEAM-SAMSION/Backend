package com.pawith.authmodule.application.service;

import com.pawith.authmodule.application.port.in.JWTExtractEmailUseCase;
import com.pawith.jwt.JWTProvider;
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
