package com.pawith.authmodule.application.service;

public interface JWTExtractEmailUseCase {
    String extractEmail(final String token);
}
