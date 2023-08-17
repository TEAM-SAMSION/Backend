package com.pawith.authmodule.application.port.out.factory;


import com.pawith.authmodule.application.dto.OAuthResponse;
import com.pawith.authmodule.application.dto.Provider;

public interface OAuthFactory {
    OAuthResponse login(Provider provider, String accessToken);
}
