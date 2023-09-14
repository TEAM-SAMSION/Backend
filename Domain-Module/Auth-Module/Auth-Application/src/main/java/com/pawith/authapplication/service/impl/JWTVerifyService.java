package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.service.JWTVerifyUseCase;
import com.pawith.authdomain.jwt.JWTProvider;
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
