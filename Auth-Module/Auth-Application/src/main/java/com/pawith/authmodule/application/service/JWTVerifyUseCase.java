package com.pawith.authmodule.application.service;

public interface JWTVerifyUseCase {
    void validateToken(final String token);
}
