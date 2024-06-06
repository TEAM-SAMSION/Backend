package com.pawith.authapplication.service.oauth.feign.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoogleUserInfo {
    private String email;
    private String name;
    private String id;
}
