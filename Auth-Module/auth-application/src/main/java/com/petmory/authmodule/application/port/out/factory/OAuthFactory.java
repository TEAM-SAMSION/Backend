package com.petmory.authmodule.application.port.out.factory;


import com.petmory.authmodule.application.dto.OAuthResponse;
import com.petmory.authmodule.application.dto.Provider;

public interface OAuthFactory {
    OAuthResponse login(Provider provider, String accessToken);
}
