package com.petmory.authmodule.application.port.in;

import com.petmory.authmodule.application.dto.OAuthResponse;
import com.petmory.authmodule.application.dto.Provider;

public interface OAuthUseCase {

    OAuthResponse oAuthLogin(Provider provider, String accessToken);

}
