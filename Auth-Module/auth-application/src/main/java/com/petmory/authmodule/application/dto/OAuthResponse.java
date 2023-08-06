package com.petmory.authmodule.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class OAuthResponse {
    private final String serverToken;
}
