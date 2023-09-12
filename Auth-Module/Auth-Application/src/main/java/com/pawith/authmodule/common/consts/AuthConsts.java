package com.pawith.authmodule.common.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthConsts {
    public static final String AUTHENTICATION_TYPE= "Bearer";
    public static final String AUTHORIZATION = "Authorization";
    public static final String EMPTY_HEADER = null;
}
