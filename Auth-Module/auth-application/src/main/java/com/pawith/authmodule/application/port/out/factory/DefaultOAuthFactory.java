package com.pawith.authmodule.application.port.out.factory;

import com.pawith.authmodule.application.dto.OAuthRequest;
import com.pawith.authmodule.application.dto.OAuthResponse;
import com.pawith.authmodule.application.dto.Provider;
import com.pawith.authmodule.application.port.out.observer.subject.AbstractOAuthSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultOAuthFactory implements OAuthFactory {
    private final AbstractOAuthSubject abstractOAuthSubject;

    @Override
    public OAuthResponse login(Provider provider, String accessToken) {
        return abstractOAuthSubject.notifyObservers(new OAuthRequest(provider, accessToken));
    }
}