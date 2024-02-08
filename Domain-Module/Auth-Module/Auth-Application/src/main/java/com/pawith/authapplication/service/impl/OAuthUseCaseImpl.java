package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.authapplication.service.OAuthUseCase;
import com.pawith.authapplication.service.oauth.OAuthInvoker;
import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.enums.Provider;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class OAuthUseCaseImpl implements OAuthUseCase {

    private final OAuthInvoker oAuthInvoker;

    @Override
    public OAuthResponse oAuthLogin(Provider provider, String accessToken, String refreshToken){
        return oAuthInvoker.execute(new OAuthRequest(provider, accessToken, refreshToken));
    }
}
