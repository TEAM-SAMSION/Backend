package com.petmory.authmodule.application.port.in;

public interface JWTVerifyUseCase {
    void validateToken(final String token);
}
