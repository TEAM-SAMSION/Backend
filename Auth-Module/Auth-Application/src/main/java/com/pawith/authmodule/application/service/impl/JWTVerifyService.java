package com.pawith.authmodule.application.service.impl;

import com.pawith.authmodule.application.service.JWTVerifyUseCase;
import com.pawith.jwt.jwt.JWTProvider;
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
