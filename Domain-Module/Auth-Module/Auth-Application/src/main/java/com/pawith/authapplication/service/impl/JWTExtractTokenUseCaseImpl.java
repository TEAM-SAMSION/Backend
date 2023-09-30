package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.service.JWTExtractTokenUseCase;
import com.pawith.authapplication.utils.TokenExtractUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JWTExtractTokenUseCaseImpl implements JWTExtractTokenUseCase {
    @Override
    public String extractToken(final String tokenHeader) {
        return TokenExtractUtils.extractToken(tokenHeader);
    }
}
