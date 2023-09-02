package com.pawith.authmodule.application.service;

import com.pawith.authmodule.application.common.consts.AuthConsts;
import com.pawith.authmodule.application.port.in.JWTExtractTokenUseCase;
import com.pawith.commonmodule.exception.Error;
import com.pawith.authmodule.application.common.exception.InvalidAuthorizationTypeException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class JWTExtractTokenService implements JWTExtractTokenUseCase {
    @Override
    public String extractToken(final String tokenHeader) {
        final String[] splitToken = tokenHeader.split(" ");
        final String authorizationType = splitToken[0];
        final String accessToken = splitToken[1];
        if(!Objects.equals(authorizationType, AuthConsts.AUTHENTICATION_TYPE)){
            throw new InvalidAuthorizationTypeException(Error.INVALID_AUTHORIZATION_TYPE);
        }
        return accessToken;
    }
}
