package com.pawith.authmodule.application.service;

import com.pawith.authmodule.application.service.dto.OAuthResponse;
import com.pawith.authmodule.application.service.dto.Provider;

public interface OAuthUseCase {

    OAuthResponse oAuthLogin(Provider provider, String accessToken);

}
