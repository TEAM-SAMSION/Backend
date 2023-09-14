package com.pawith.authmodule.service.impl;

import com.pawith.authmodule.service.OAuthUseCase;
import com.pawith.authmodule.service.dto.OAuthRequest;
import com.pawith.authmodule.service.dto.OAuthResponse;
import com.pawith.authmodule.service.dto.Provider;
import com.pawith.authmodule.service.command.OAuthInvoker;
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
