package com.pawith.authapplication.service.command.handler.impl;

import com.pawith.authapplication.service.command.handler.AuthHandler;
import com.pawith.authapplication.service.dto.OAuthRequest;
import com.pawith.authapplication.service.dto.OAuthUserInfo;
import com.pawith.authapplication.service.dto.Provider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GoogleOAuthHandler implements AuthHandler {
    private static final Provider OAUTH_TYPE = Provider.GOOGLE;
    private static final String GOOGLE_OAUTH_USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
    public static final String GOOGLE_AUTHORIZATION = "Authorization";
    public static final String GOOGLE_AUTHORIZATION_BEARER = "Bearer ";

    @Override
    public OAuthUserInfo handle(OAuthRequest authenticationInfo) {
        final String accessToken = authenticationInfo.getAccessToken();
        final GoogleUserInfo googleUserInfo = getGoogleUserInfo(accessToken);
        return new OAuthUserInfo(googleUserInfo.getName(), googleUserInfo.getEmail());
    }

    @Override
    public boolean isAccessible(OAuthRequest authInfo) {
        return OAUTH_TYPE.equals(authInfo.getProvider());
    }

    private GoogleUserInfo getGoogleUserInfo(final String accessToken){
        return WebClient.create(GOOGLE_OAUTH_USER_INFO_URL)
            .get()
            .header(GOOGLE_AUTHORIZATION, GOOGLE_AUTHORIZATION_BEARER+accessToken)
            .retrieve()
            .bodyToMono(GoogleUserInfo.class)
            .block();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class GoogleUserInfo{
        private String email;
        private String name;

        public GoogleUserInfo(String email, String name) {
            this.email = email;
            this.name = name;
        }
    }
}