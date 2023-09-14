package com.pawith.authapplication.service;

import com.pawith.authapplication.service.dto.OAuthResponse;
import com.pawith.authapplication.service.dto.Provider;

public interface OAuthUseCase {

    OAuthResponse oAuthLogin(Provider provider, String accessToken);

}
