package com.pawith.authmodule.application.port.in;

public interface JWTExtractEmailUseCase {
    String extractEmail(final String token);
}
