package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.service.OAuthUseCase;
import com.pawith.authapplication.service.dto.OAuthRequest;
import com.pawith.authapplication.service.dto.OAuthResponse;
import com.pawith.authapplication.service.dto.Provider;
import com.pawith.authapplication.service.command.OAuthInvoker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuthUseCase {

    private final OAuthInvoker oAuthInvoker;

    @Override
    public OAuthResponse oAuthLogin(Provider provider, String accessToken){
        return oAuthInvoker.execute(new OAuthRequest(provider, accessToken));
    }
}
