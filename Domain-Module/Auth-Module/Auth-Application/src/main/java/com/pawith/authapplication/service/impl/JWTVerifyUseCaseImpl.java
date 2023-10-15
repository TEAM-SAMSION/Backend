package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.service.JWTVerifyUseCase;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.commonmodule.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class JWTVerifyUseCaseImpl implements JWTVerifyUseCase {
    private final JWTProvider jwtProvider;

    @Override
    public void validateToken(final String token){
        jwtProvider.validateToken(token);
    }
}
