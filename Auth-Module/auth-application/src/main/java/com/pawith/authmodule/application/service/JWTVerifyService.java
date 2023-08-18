package com.pawith.authmodule.application.service;

import com.pawith.authmodule.application.port.in.JWTVerifyUseCase;
import com.pawith.jwt.JWTProvider;
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
