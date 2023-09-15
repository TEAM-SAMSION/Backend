package com.pawith.authapplication.service.command.handler;

import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthUserInfo;

public interface AuthHandler{
    OAuthUserInfo handle(OAuthRequest authenticationInfo);

    boolean isAccessible(OAuthRequest authenticationInfo);
}
