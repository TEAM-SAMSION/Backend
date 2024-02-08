package com.pawith.authapplication.service;

import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.commonmodule.enums.Provider;

public interface OAuthUseCase {

    OAuthResponse oAuthLogin(Provider provider, String accessToken, String refreshToken);

}
