package com.pawith.jwt.jwt;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class JWTTestConsts {
    public static final String SECRET = "secretsecretsecretsecretsecretsecretsecretsecret";
    public static final Long ACCESS_TOKEN_EXPIRED_TIME = 1000L;
    public static final Long REFRESH_TOKEN_EXPIRED_TIME = 1000L;
}
