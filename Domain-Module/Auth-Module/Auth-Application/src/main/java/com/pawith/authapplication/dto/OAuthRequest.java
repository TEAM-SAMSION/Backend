package com.pawith.authapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.pawith.commonmodule.enums.Provider;

@Getter
@AllArgsConstructor
public class OAuthRequest {
    private Provider Provider;
    private String accessToken;
}
