package com.pawith.authapplication.handler.request;

import com.pawith.authdomain.jwt.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnusedTokenExpireEvent {
    private final String email;
    private final TokenType tokenType;
}
