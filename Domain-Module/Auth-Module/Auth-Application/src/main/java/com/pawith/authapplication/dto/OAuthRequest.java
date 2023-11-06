package com.pawith.authapplication.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.pawith.commonmodule.enums.Provider;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthRequest {
    private Provider provider;
    private String accessToken;
}
