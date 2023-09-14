package com.pawith.authapplication.service;

public interface JWTVerifyUseCase {
    void validateToken(final String token);
}
