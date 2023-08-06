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
public class GoogleOAuthObserver  extends AbstractOAuthObserver {
    private static final Provider PROVIDER = Provider.GOOGLE;
    private final RestTemplate restTemplate;

    @Override
    protected OAuthUserInfo attemptLogin(String accessToken) {
        log.info("GoogleOAuthObserver attemptLogin");
        return new OAuthUserInfo("Google username","Google email");
    }

    @Override
    public boolean isLogin(Provider provider) {
        log.info("GoogleOAuthObserver isLogin");
        return PROVIDER.equals(provider);
    }
}
