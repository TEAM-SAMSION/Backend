package com.pawith.authapplication.service;

import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.authapplication.dto.Provider;

public interface OAuthUseCase {

    OAuthResponse oAuthLogin(Provider provider, String accessToken);

}
