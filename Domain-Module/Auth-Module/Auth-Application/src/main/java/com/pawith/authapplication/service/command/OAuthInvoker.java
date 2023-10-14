package com.pawith.authapplication.service.command;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.exception.AuthException;
import com.pawith.authapplication.handler.request.UnusedTokenExpireEvent;
import com.pawith.authapplication.service.command.handler.AuthHandler;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.commonmodule.exception.Error;
import com.pawith.commonmodule.event.UserSignUpEvent;
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
        publishEvent(request, oAuthUserInfo);
        return generateServerAuthenticationTokens(oAuthUserInfo);
    }

    private void publishEvent(OAuthRequest request, OAuthUserInfo oAuthUserInfo) {
        publisher.publishEvent(new UserSignUpEvent(oAuthUserInfo.getUsername(), oAuthUserInfo.getEmail(), request.getProvider()));
        publisher.publishEvent(new UnusedTokenExpireEvent(oAuthUserInfo.getEmail(), TokenType.REFRESH_TOKEN));
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
