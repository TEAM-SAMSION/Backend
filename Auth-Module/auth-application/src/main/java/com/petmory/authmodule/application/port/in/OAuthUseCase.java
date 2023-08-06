package com.petmory.authmodule.application.port.in;

import com.petmory.authmodule.adaptor.api.request.Provider;
import com.petmory.authmodule.adaptor.api.response.OAuthResponse;

public interface OAuthUseCase {

    OAuthResponse oAuthLogin(Provider provider, String accessToken);

}
