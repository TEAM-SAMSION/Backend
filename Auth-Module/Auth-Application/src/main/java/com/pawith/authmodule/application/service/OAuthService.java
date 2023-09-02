package com.pawith.authmodule.application.service;

import com.pawith.authmodule.application.dto.OAuthRequest;
import com.pawith.authmodule.application.dto.OAuthResponse;
import com.pawith.authmodule.application.dto.Provider;
import com.pawith.authmodule.application.port.in.OAuthUseCase;
import com.pawith.authmodule.application.port.out.command.OAuthInvoker;
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
