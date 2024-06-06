package com.pawith.authapplication.service.oauth.feign.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoUserInfo {
    private static final String EMAIL = "email";
    private static final String NAME = "nickname";
    private static final String PROFILE = "profile";
    @JsonAlias("kakao_account")
    private Map<String, Object> kakaoAccount;

    @SuppressWarnings("unchecked")
    public String getNickname(){
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get(PROFILE);
        return (String) profile.get(NAME);
    }

    public String getEmail(){
        return (String)kakaoAccount.get(EMAIL);
    }
}
