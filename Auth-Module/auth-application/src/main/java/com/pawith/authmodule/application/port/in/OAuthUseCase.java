package com.pawith.authmodule.application.port.in;

import com.pawith.authmodule.application.dto.OAuthResponse;
import com.pawith.authmodule.application.dto.Provider;

public interface OAuthUseCase {

    OAuthResponse oAuthLogin(Provider provider, String accessToken);

}
