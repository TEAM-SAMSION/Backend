package com.pawith.authapplication.service.command.feign.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoogleUserInfo {
    private String email;
    private String name;
    public GoogleUserInfo(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
