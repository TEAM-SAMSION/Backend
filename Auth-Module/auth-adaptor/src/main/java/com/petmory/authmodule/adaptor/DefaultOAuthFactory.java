package com.petmory.authmodule.adaptor;

import com.petmory.authmodule.adaptor.api.request.Provider;
import com.petmory.authmodule.adaptor.api.response.OAuthResponse;
import com.petmory.authmodule.application.dto.OAuthRequest;
import com.petmory.authmodule.application.port.out.factory.OAuthFactory;
import com.petmory.authmodule.application.port.out.observer.subject.AbstractOAuthSubject;
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