package com.pawith.authapplication.service.oauth.impl;

import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.service.oauth.AuthHandler;
import com.pawith.authapplication.service.oauth.feign.GoogleOAuthFeignClient;
import com.pawith.authapplication.service.oauth.feign.response.GoogleUserInfo;
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
        return new OAuthUserInfo(googleUserInfo.getName(), googleUserInfo.getEmail(), googleUserInfo.getId());
    }

    @Override
    public boolean isAccessible(OAuthRequest authInfo) {
        return OAUTH_TYPE.equals(authInfo.getProvider());
    }

    private GoogleUserInfo getGoogleUserInfo(final String accessToken){
        return googleOAuthFeignClient.getGoogleUserInfo(GOOGLE_AUTHORIZATION_BEARER+accessToken);
    }
}
