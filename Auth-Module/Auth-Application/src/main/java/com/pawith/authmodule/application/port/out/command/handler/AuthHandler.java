package com.pawith.authmodule.application.port.out.command.handler;

import com.pawith.authmodule.application.dto.OAuthRequest;
import com.pawith.authmodule.application.dto.OAuthUserInfo;

public interface AuthHandler{
    OAuthUserInfo handle(OAuthRequest authenticationInfo);

    boolean isAccessible(OAuthRequest authenticationInfo);
}
