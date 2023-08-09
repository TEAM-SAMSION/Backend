package com.petmory.authmodule.application.port.in;

public interface JWTExtractTokenUseCase {
    String extractToken(final String tokenHeader);
}
