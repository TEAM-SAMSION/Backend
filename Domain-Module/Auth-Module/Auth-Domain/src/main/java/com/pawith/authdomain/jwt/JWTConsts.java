package com.pawith.authdomain.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JWTConsts {
    public static final String TOKEN_ISSUER = "pawith";
    public static final String USER_CLAIMS = "user_claims";
    public static final String TOKEN_TYPE = "token_type";
}
