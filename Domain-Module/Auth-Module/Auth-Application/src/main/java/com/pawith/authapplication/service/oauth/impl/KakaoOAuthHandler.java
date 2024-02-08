package com.pawith.authapplication.service.oauth.impl;

import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.service.oauth.AuthHandler;
import com.pawith.authapplication.service.oauth.feign.KakaoOAuthFeignClient;
import com.pawith.authapplication.service.oauth.feign.response.KakaoUserInfo;
import com.pawith.authapplication.service.oauth.feign.response.TokenInfo;
import com.pawith.authdomain.exception.AuthError;
import com.pawith.authdomain.jwt.exception.InvalidTokenException;
import com.pawith.commonmodule.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoOAuthHandler implements AuthHandler {
    private static final Provider OAUTH_TYPE = Provider.KAKAO;
    private final KakaoOAuthFeignClient kakaoOAuthFeignClient;
    private static final String KAKAO_AUTHORIZATION_BEARER = "Bearer ";
    @Value("${app-id.kakao}")
    private String appId;

    @Override
    public OAuthUserInfo handle(OAuthRequest authenticationInfo) {
        // Access Token 검증
        final TokenInfo tokenInfo = getKakaoTokenInfo(authenticationInfo.getAccessToken());
        if (!tokenInfo.getAppId().equals(appId)) throw new InvalidTokenException(AuthError.INVALID_TOKEN);

        final KakaoUserInfo kakaoUserInfo = getKaKaoUserInfo(authenticationInfo.getAccessToken());
        return new OAuthUserInfo(kakaoUserInfo.getNickname(), kakaoUserInfo.getEmail(), null);
    }

    @Override
    public boolean isAccessible(OAuthRequest authInfo) {
        return OAUTH_TYPE.equals(authInfo.getProvider());
    }

    private TokenInfo getKakaoTokenInfo(String accessToken) {
        return kakaoOAuthFeignClient.getKakaoTokenInfo(KAKAO_AUTHORIZATION_BEARER + accessToken);
    }

    private KakaoUserInfo getKaKaoUserInfo(String accessToken) {
        return kakaoOAuthFeignClient.getKakaoUserInfo(KAKAO_AUTHORIZATION_BEARER + accessToken);
    }

}
