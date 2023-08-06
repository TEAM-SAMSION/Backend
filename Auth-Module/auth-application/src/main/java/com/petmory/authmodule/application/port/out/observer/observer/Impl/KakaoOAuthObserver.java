package com.petmory.authmodule.application.port.out.observer.observer.Impl;

import com.petmory.authmodule.adaptor.api.request.Provider;
import com.petmory.authmodule.application.dto.OAuthUserInfo;
import com.petmory.authmodule.application.port.out.observer.observer.AbstractOAuthObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOAuthObserver extends AbstractOAuthObserver {
    private static final Provider PROVIDER = Provider.KAKAO;
    private final RestTemplate restTemplate;

    @Override
    protected OAuthUserInfo attemptLogin(String accessToken) {
        log.info("KakaoOAuthObserver attemptLogin");
        return new OAuthUserInfo("Kakao username","Kakao email");
    }

    @Override
    public boolean isLogin(Provider provider) {
        log.info("KakaoOAuthObserver isLogin");
        return PROVIDER.equals(provider);
    }
}
