package com.pawith.authapplication.service.command.handler.impl;

import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.service.command.feign.GoogleOAuthFeignClient;
import com.pawith.authapplication.service.command.feign.response.GoogleUserInfo;
import com.pawith.authapplication.service.command.handler.AuthHandler;
import com.pawith.commonmodule.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleOAuthHandler implements AuthHandler {
    private static final Provider OAUTH_TYPE = Provider.GOOGLE;
    private static final String GOOGLE_AUTHORIZATION_BEARER = "Bearer ";

    private final GoogleOAuthFeignClient googleOAuthFeignClient;

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
        return googleOAuthFeignClient.getGoogleUserInfo(GOOGLE_AUTHORIZATION_BEARER+accessToken);
    }
}
