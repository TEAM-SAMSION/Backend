package com.pawith.authapplication.utils;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.exception.InvalidAuthorizationTypeException;
import com.pawith.commonmodule.exception.Error;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenExtractUtils {

    public static String extractToken(final String tokenHeader) {
        if(!StringUtils.hasText(tokenHeader)){
            throw new InvalidAuthorizationTypeException(Error.EMPTY_AUTHORIZATION_HEADER);
        }

        final String[] splitToken = tokenHeader.split(" ");
        final String authorizationType = splitToken[0];
        final String token = splitToken[1];
        if(!Objects.equals(authorizationType, AuthConsts.AUTHENTICATION_TYPE)){
            throw new InvalidAuthorizationTypeException(Error.INVALID_AUTHORIZATION_TYPE);
        }
        return token;
    }
}
