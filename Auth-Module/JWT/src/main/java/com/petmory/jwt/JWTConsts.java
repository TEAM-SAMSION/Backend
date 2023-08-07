package com.petmory.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JWTConsts {
    public static final String TOKEN_ISSUER = "petmory";
    public static final String EMAIL ="email";
    public static final String TOKEN_TYPE = "token_type";
}
