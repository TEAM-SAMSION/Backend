package com.petmory.authmodule.application.service;

import com.petmory.authmodule.adaptor.api.request.Provider;
import com.petmory.authmodule.adaptor.api.response.OAuthResponse;
import com.petmory.authmodule.application.port.in.OAuthUseCase;
import com.petmory.authmodule.application.port.out.factory.OAuthFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuthUseCase {

    private final OAuthFactory oAuthFactory;

    @Override
    public OAuthResponse oAuthLogin(Provider provider, String accessToken){
        return oAuthFactory.login(provider, accessToken);
    }
}
