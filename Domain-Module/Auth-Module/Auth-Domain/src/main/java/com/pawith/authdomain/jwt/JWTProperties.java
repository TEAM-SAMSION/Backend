package com.pawith.authdomain.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
    private final String secret;
    private final long accessTokenExpirationTime;
    private final long refreshTokenExpirationTime;
}
