package com.pawith.authmodule.application.port.out.command.handler.impl;

import com.pawith.authmodule.application.dto.OAuthRequest;
import com.pawith.authmodule.application.dto.OAuthUserInfo;
import com.pawith.authmodule.application.dto.Provider;
import com.pawith.authmodule.application.port.out.command.handler.AuthHandler;
import org.springframework.stereotype.Component;

@Component
public class NaverOAuthHandler implements AuthHandler {
    private static final Provider OAUTH_TYPE = Provider.NAVER;
    @Override
    public OAuthUserInfo handle(OAuthRequest authenticationInfo) {
        return new OAuthUserInfo("naver", "naver");
    }

    @Override
    public boolean isAccessible(OAuthRequest authInfo) {
        return OAUTH_TYPE.equals(authInfo.getProvider());
    }
}
