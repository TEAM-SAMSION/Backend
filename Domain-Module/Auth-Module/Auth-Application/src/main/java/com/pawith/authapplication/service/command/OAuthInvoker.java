package com.pawith.authapplication.service.command;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.exception.AuthException;
import com.pawith.authapplication.service.command.handler.AuthHandler;
import com.pawith.authdomain.exception.AuthError;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.authdomain.service.TokenSaveService;
import com.pawith.commonmodule.event.UserSignUpEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuthInvoker {
    private final List<AuthHandler> authHandlerList;
    private final JWTProvider jwtProvider;
    private final TokenSaveService tokenSaveService;
    private final ApplicationEventPublisher publisher;
    public OAuthResponse execute(OAuthRequest request){
        OAuthUserInfo oAuthUserInfo = attemptLogin(request);
        publishEvent(request, oAuthUserInfo);
        return generateServerAuthenticationTokens(oAuthUserInfo);
    }

    private void publishEvent(OAuthRequest request, OAuthUserInfo oAuthUserInfo) {
        publisher.publishEvent(new UserSignUpEvent(oAuthUserInfo.getUsername(), oAuthUserInfo.getEmail(), request.getProvider()));
//        publisher.publishEvent(new UnusedTokenExpireEvent(oAuthUserInfo.getEmail(), TokenType.REFRESH_TOKEN));
    }

    private OAuthUserInfo attemptLogin(OAuthRequest request) {
        for (AuthHandler authHandler : authHandlerList) {
            if (authHandler.isAccessible(request)) {
                return authHandler.handle(request);
            }
        }
        throw new AuthException(AuthError.OAUTH_FAIL);
    }

    private OAuthResponse generateServerAuthenticationTokens(OAuthUserInfo oAuthUserInfo) {
        final JWTProvider.Token token = jwtProvider.generateToken(oAuthUserInfo.getEmail());
        tokenSaveService.saveToken(token.refreshToken(), oAuthUserInfo.getEmail(), TokenType.REFRESH_TOKEN);
        final String accessToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.accessToken();
        final String refreshToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.refreshToken();
        return OAuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
