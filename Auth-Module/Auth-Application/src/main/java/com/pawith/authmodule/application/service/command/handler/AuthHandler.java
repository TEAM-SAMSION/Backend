package com.pawith.authmodule.application.service.command.handler;

import com.pawith.authmodule.application.service.dto.OAuthRequest;
import com.pawith.authmodule.application.service.dto.OAuthUserInfo;

public interface AuthHandler{
    OAuthUserInfo handle(OAuthRequest authenticationInfo);

    boolean isAccessible(OAuthRequest authenticationInfo);
}
