package com.pawith.authmodule.application.service.command.handler.impl;

import com.pawith.authmodule.application.service.command.handler.AuthHandler;
import com.pawith.authmodule.application.service.dto.OAuthRequest;
import com.pawith.authmodule.application.service.dto.OAuthUserInfo;
import com.pawith.authmodule.application.service.dto.Provider;
import com.pawith.commonmodule.exception.Error;
import com.pawith.jwt.jwt.exception.InvalidTokenException;
import lombok.AccessLevel;
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

        final KakaoUserInfo kakaoUserInfo = getKaKaoUserInfo(authenticationInfo.getAccessToken());
        return new OAuthUserInfo(kakaoUserInfo.kakaoAccount.getNickname(), kakaoUserInfo.kakaoAccount.getEmail());
    }

    @Override
    public boolean isAccessible(OAuthRequest authInfo) {
        return OAUTH_TYPE.equals(authInfo.getProvider());
    }

    private KakaoUserInfo getKaKaoUserInfo(String accessToken){
        return WebClient.create(KAKAO_OAUTH_USER_INFO_URL)
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .header(KAKAO_AUTHORIZATION, KAKAO_AUTHORIZATION_BEARER+accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfo.class)
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
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class KakaoUserInfo{
        private KakaoAccount kakaoAccount;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class KakaoAccount{
        private static final String EMAIL = "email";
        private static final String NAME = "name";
        private Map<String, String> response;
        public String getNickname(){
            return response.get(NAME);
        }

        public String getEmail(){
            return response.get(EMAIL);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class TokenInfo{
        private static final String APPID = "appId";

        private Map<String, String> response;

        public String getAppId(){
            return response.get(APPID);
        }
    }
}
