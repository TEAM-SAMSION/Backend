package com.pawith.authmodule.service.impl;

import com.pawith.authmodule.exception.InvalidAuthorizationTypeException;
import com.pawith.authmodule.service.JWTExtractTokenUseCase;
import com.pawith.authmodule.common.consts.AuthConsts;
import com.pawith.commonmodule.exception.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Slf4j
@Service
public class JWTExtractTokenService implements JWTExtractTokenUseCase {
    @Override
    public String extractToken(final String tokenHeader) {
        if(!StringUtils.hasText(tokenHeader)){
            throw new InvalidAuthorizationTypeException(Error.EMPTY_AUTHORIZATION_HEADER);
        }

        final String[] splitToken = tokenHeader.split(" ");
        final String authorizationType = splitToken[0];
        final String accessToken = splitToken[1];
        if(!Objects.equals(authorizationType, AuthConsts.AUTHENTICATION_TYPE)){
            throw new InvalidAuthorizationTypeException(Error.INVALID_AUTHORIZATION_TYPE);
        }
        return accessToken;
    }
}
