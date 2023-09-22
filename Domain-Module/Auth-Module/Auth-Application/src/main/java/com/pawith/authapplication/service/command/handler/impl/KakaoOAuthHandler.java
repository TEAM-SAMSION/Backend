package com.pawith.authapplication.service.command.handler.impl;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.service.command.handler.AuthHandler;
import com.pawith.authdomain.jwt.exception.InvalidTokenException;
import com.pawith.commonmodule.enums.Provider;
import com.pawith.commonmodule.exception.Error;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class KakaoOAuthHandler implements AuthHandler {
    private static final Provider OAUTH_TYPE = Provider.KAKAO;
    private static final String KAKAO_OAUTH_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String KAKAO_OAUTH_TOKEN_INFO_URL = "https://kapi.kakao.com/v1/user/access_token_info";
    private static final String KAKAO_AUTHORIZATION = "Authorization";
    private static final String KAKAO_AUTHORIZATION_BEARER = "Bearer ";
    @Value("${app-id.kakao}")
    private String appId;

    @Override
    public OAuthUserInfo handle(OAuthRequest authenticationInfo) {
        // Access Token 검증
        final TokenInfo tokenInfo = verifyAccessToken(authenticationInfo.getAccessToken());
        if (!tokenInfo.getAppId().equals(appId)) throw new InvalidTokenException(Error.INVALID_TOKEN);

        final KakaoAccount kakaoAccount = getKaKaoUserInfo(authenticationInfo.getAccessToken());
        return new OAuthUserInfo(kakaoAccount.getNickname(), kakaoAccount.getEmail());
    }

    @Override
    public boolean isAccessible(OAuthRequest authInfo) {
        return OAUTH_TYPE.equals(authInfo.getProvider());
    }

    private KakaoAccount getKaKaoUserInfo(String accessToken){
        return WebClient.create(KAKAO_OAUTH_USER_INFO_URL)
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .header(KAKAO_AUTHORIZATION, KAKAO_AUTHORIZATION_BEARER+accessToken)
                .retrieve()
                .bodyToMono(KakaoAccount.class)
                .block();
    }

    public TokenInfo verifyAccessToken(String accessToken) {
        return WebClient.create(KAKAO_OAUTH_TOKEN_INFO_URL)
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .header(KAKAO_AUTHORIZATION, KAKAO_AUTHORIZATION_BEARER + accessToken)
                .retrieve()
                .bodyToMono(TokenInfo.class)
                .block();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class KakaoAccount{
        private static final String EMAIL = "email";
        private static final String NAME = "nickname";
        private static final String PROFILE = "profile";
        @JsonAlias("kakao_account")
        private Map<String, Object> kakakaoAccount;
        public String getNickname(){
            Map<String, Object> profile = (Map<String, Object>) kakakaoAccount.get(PROFILE);
            return (String) profile.get(NAME);
        }

        public String getEmail(){
            return (String)kakakaoAccount.get(EMAIL);
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class TokenInfo{
        @JsonAlias("app_id")
        private String appId;
    }
}
