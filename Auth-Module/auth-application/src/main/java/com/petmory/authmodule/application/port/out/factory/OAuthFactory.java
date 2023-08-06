package com.petmory.authmodule.application.port.out.factory;

import com.petmory.authmodule.adaptor.api.request.Provider;
import com.petmory.authmodule.adaptor.api.response.OAuthResponse;

public interface OAuthFactory {
    OAuthResponse login(Provider provider, String accessToken);
}
