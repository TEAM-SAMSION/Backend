package com.pawith.authmodule.application.service;

public interface JWTExtractTokenUseCase {
    String extractToken(final String tokenHeader);
}
