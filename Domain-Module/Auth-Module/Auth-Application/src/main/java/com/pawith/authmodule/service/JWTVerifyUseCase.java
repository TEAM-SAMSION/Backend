package com.pawith.authmodule.service;

public interface JWTVerifyUseCase {
    void validateToken(final String token);
}
