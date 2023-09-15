package com.pawith.authapplication.service.command.handler.impl;

import com.pawith.authapplication.service.command.handler.AuthHandler;
import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.dto.Provider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class NaverOAuthHandler implements AuthHandler {
    private static final Provider OAUTH_TYPE = Provider.NAVER;
    private static final String NAVER_OAUTH_USER_INFO_URL = "https://openapi.naver.com/v1/nid/me";
    private static final String NAVER_AUTHORIZATION = "Authorization";
    private static final String NAVER_AUTHORIZATION_BEARER = "Bearer ";

    @Override
    public OAuthUserInfo handle(OAuthRequest authenticationInfo) {
        final NaverUserInfo naverUserInfo = getNaverUserInfo(authenticationInfo.getAccessToken());
        return new OAuthUserInfo(naverUserInfo.getNickname(), naverUserInfo.getEmail());
    }

    @Override
    public boolean isAccessible(OAuthRequest authInfo) {
        return OAUTH_TYPE.equals(authInfo.getProvider());
    }

    private NaverUserInfo getNaverUserInfo(String accessToken){
        return WebClient.create(NAVER_OAUTH_USER_INFO_URL)
            .get()
            .accept(MediaType.APPLICATION_JSON)
            .header(NAVER_AUTHORIZATION, NAVER_AUTHORIZATION_BEARER+accessToken)
            .retrieve()
            .bodyToMono(NaverUserInfo.class)
            .block();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class NaverUserInfo{
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
}
