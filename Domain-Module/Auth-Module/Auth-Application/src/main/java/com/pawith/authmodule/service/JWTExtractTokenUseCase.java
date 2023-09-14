package com.pawith.authmodule.service;

public interface JWTExtractTokenUseCase {
    String extractToken(final String tokenHeader);
}
