package com.pawith.authmodule.application.service.command;

import com.pawith.authmodule.application.exception.AuthException;
import com.pawith.authmodule.common.consts.AuthConsts;
import com.pawith.authmodule.application.service.dto.OAuthRequest;
import com.pawith.authmodule.application.service.dto.OAuthResponse;
import com.pawith.authmodule.application.service.dto.OAuthUserInfo;
import com.pawith.authmodule.application.service.command.handler.AuthHandler;
import com.pawith.commonmodule.exception.Error;
import com.pawith.jwt.jwt.JWTProvider;
import com.pawith.usermodule.application.handler.event.UserSignUpEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OAuthInvoker {
    private final List<AuthHandler> authHandlerList;
    private final JWTProvider jwtProvider;
    private final ApplicationEventPublisher publisher;

    private static final String AUTHENTICATION_TYPE = AuthConsts.AUTHENTICATION_TYPE + " ";

    public OAuthResponse execute(OAuthRequest request){
        OAuthUserInfo oAuthUserInfo = attemptLogin(request);
        publisher.publishEvent(new UserSignUpEvent(oAuthUserInfo.getUsername(), oAuthUserInfo.getEmail(), request.getProvider().toString()));
        return generateServerAuthenticationTokens(oAuthUserInfo);
    }

    private OAuthUserInfo attemptLogin(OAuthRequest request) {
        for (AuthHandler authHandler : authHandlerList) {
            if (authHandler.isAccessible(request)) {
                return authHandler.handle(request);
            }
        }
        throw new AuthException(Error.OAUTH_FAIL);
    }

    private OAuthResponse generateServerAuthenticationTokens(OAuthUserInfo oAuthUserInfo) {
        final String accessToken = attachAuthenticationType(jwtProvider::generateAccessToken, oAuthUserInfo.getEmail());
        final String refreshToken = attachAuthenticationType(jwtProvider::generateRefreshToken, oAuthUserInfo.getEmail());
        return OAuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    private <T> String attachAuthenticationType(Function<T, String> generateTokenMethod, T includeClaimData) {
        return AUTHENTICATION_TYPE + generateTokenMethod.apply(includeClaimData);
    }
}