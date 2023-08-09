package com.petmory.authmodule.application.service;

import com.petmory.authmodule.application.port.in.JWTVerifyUseCase;
import com.petmory.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JWTVerifyService implements JWTVerifyUseCase {
    private final JWTProvider jwtProvider;

    @Override
    public void validateToken(final String token){
        jwtProvider.validateToken(token);
    }
}
