package com.pawith.jwt.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
    private final String secret;
    private final long accessTokenExpirationTime;
    private final long refreshTokenExpirationTime;
}
