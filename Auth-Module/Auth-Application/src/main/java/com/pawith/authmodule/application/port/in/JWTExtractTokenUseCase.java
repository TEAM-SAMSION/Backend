package com.pawith.authmodule.application.port.in;

public interface JWTExtractTokenUseCase {
    String extractToken(final String tokenHeader);
}
