package com.pawith.authmodule.application.port.out.observer.observer;

import com.pawith.authmodule.application.dto.OAuthUserInfo;
import com.pawith.authmodule.application.dto.Provider;
import com.pawith.authmodule.application.port.out.observer.subject.OAuth;
import com.pawith.commonmodule.observer.observer.Observer;

public abstract class AbstractOAuthObserver implements Observer<OAuth, OAuthUserInfo> {
    @Override
    public OAuthUserInfo accept(OAuth status) {
        return attemptLogin(status.getAccessToken());
    }

    protected abstract OAuthUserInfo attemptLogin(String accessToken);

    public abstract boolean isLogin(Provider provider);

}
