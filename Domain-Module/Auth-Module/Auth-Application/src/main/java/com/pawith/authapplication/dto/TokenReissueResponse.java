package com.pawith.authapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class TokenReissueResponse {
    private String accessToken;
    private String refreshToken;
}
