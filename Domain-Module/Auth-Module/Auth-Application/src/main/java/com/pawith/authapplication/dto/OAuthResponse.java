package com.pawith.authapplication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class OAuthResponse {
    private final String accessToken;
    private final String refreshToken;
}
