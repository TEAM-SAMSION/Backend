package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.service.JWTExtractTokenUseCase;
import com.pawith.authapplication.utils.TokenExtractUtils;
import com.pawith.commonmodule.annotation.ApplicationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationService
public class JWTExtractTokenUseCaseImpl implements JWTExtractTokenUseCase {
    @Override
    public String extractToken(final String tokenHeader) {
        return TokenExtractUtils.extractToken(tokenHeader);
    }
}
