package com.pawith.authmodule.application.port.out.command.handler.impl;

import com.pawith.authmodule.application.dto.OAuthRequest;
import com.pawith.authmodule.application.dto.OAuthUserInfo;
import com.pawith.authmodule.application.dto.Provider;
import com.pawith.authmodule.application.port.out.command.handler.AuthHandler;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuthHandler implements AuthHandler {
    private static final Provider OAUTH_TYPE = Provider.KAKAO;
    @Override
    public OAuthUserInfo handle(OAuthRequest authenticationInfo) {
        return new OAuthUserInfo("kakao", "kakao");
    }

    @Override
    public boolean isAccessible(OAuthRequest authInfo) {
        return OAUTH_TYPE.equals(authInfo.getProvider());
    }
}
