package com.pawith.authmodule.service.command.handler;

import com.pawith.authmodule.service.dto.OAuthRequest;
import com.pawith.authmodule.service.dto.OAuthUserInfo;

public interface AuthHandler{
    OAuthUserInfo handle(OAuthRequest authenticationInfo);

    boolean isAccessible(OAuthRequest authenticationInfo);
}
