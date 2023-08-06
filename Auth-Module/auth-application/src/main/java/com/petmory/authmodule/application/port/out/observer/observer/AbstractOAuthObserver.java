package com.petmory.authmodule.application.port.out.observer.observer;


import com.petmory.authmodule.adaptor.api.request.Provider;
import com.petmory.authmodule.application.dto.OAuthUserInfo;
import com.petmory.authmodule.application.port.out.observer.subject.OAuth;
import com.petmory.commonmodule.observer.Observer;

public abstract class AbstractOAuthObserver implements Observer<OAuth, OAuthUserInfo> {
    @Override
    public OAuthUserInfo accept(OAuth status) {
        return attemptLogin(status.getAccessToken());
    }

    protected abstract OAuthUserInfo attemptLogin(String accessToken);

    public abstract boolean isLogin(Provider provider);

}
