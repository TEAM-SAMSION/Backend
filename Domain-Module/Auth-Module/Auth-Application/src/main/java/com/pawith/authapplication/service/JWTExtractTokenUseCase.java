package com.pawith.authapplication.service;

public interface JWTExtractTokenUseCase {
    String extractToken(final String tokenHeader);
}
