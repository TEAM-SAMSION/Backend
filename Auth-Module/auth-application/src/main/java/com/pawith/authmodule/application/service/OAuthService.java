package com.pawith.authmodule.application.service;

import com.pawith.authmodule.application.dto.OAuthResponse;
import com.pawith.authmodule.application.dto.Provider;
import com.pawith.authmodule.application.port.in.OAuthUseCase;
import com.pawith.authmodule.application.port.out.factory.OAuthFactory;
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
