package com.pawith.authmodule.service;

import com.pawith.authmodule.service.dto.OAuthResponse;
import com.pawith.authmodule.service.dto.Provider;

public interface OAuthUseCase {

    OAuthResponse oAuthLogin(Provider provider, String accessToken);

}
