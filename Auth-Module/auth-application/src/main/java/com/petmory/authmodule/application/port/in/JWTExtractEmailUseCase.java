package com.petmory.authmodule.application.port.in;

public interface JWTExtractEmailUseCase {
    String extractEmail(final String token);
}
