package com.pawith.authmodule.application.port.out.factory;

import com.pawith.authmodule.application.dto.OAuthRequest;
import com.pawith.authmodule.application.dto.OAuthResponse;
import com.pawith.authmodule.application.dto.Provider;
import com.pawith.authmodule.application.port.out.observer.subject.OAuthSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultOAuthFactory implements OAuthFactory {
    private final OAuthSubject OAuthSubject;

    @Override
    public OAuthResponse login(Provider provider, String accessToken) {
        return OAuthSubject.notifyObservers(new OAuthRequest(provider, accessToken));
    }
}