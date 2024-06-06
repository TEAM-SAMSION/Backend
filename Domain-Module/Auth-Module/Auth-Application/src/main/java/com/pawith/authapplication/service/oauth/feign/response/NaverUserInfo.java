package com.pawith.authapplication.service.oauth.feign.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NaverUserInfo {
    private static final String EMAIL = "email";
    private static final String NICKNAME = "nickname";
    private Map<String, String> response;

    public String getNickname(){
        return response.get(NICKNAME);
    }

    public String getEmail(){
        return response.get(EMAIL);
    }
}
