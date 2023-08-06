package com.petmory.authmodule.application.dto;

import com.petmory.authmodule.adaptor.api.request.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthRequest {
    private Provider Provider;
    private String accessToken;
}
