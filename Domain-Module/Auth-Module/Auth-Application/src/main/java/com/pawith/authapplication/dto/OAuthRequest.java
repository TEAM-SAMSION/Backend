package com.pawith.authapplication.dto;

import com.pawith.commonmodule.enums.Provider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthRequest {
    private Provider provider;
    private String accessToken;
    private String refreshToken; // nullable
}
