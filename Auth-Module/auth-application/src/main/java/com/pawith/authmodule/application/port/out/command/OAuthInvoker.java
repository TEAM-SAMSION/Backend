package com.pawith.authmodule.application.port.out.command;

import com.pawith.authmodule.application.common.consts.AuthConsts;
import com.pawith.authmodule.application.dto.OAuthRequest;
import com.pawith.authmodule.application.dto.OAuthResponse;
import com.pawith.authmodule.application.dto.OAuthUserInfo;
import com.pawith.authmodule.application.port.out.command.handler.AuthHandler;
import com.pawith.jwt.JWTProvider;
import com.pawith.usermodule.application.handler.event.UserSignUpEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class OAuthInvoker {
    private List<AuthHandler> authHandlerList;
    private JWTProvider jwtProvider;
    private ApplicationEventPublisher publisher;

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
        throw new IllegalArgumentException("로그인 실패");
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
        return AuthConsts.AUTHENTICATION_TYPE + generateTokenMethod.apply(includeClaimData);
    }

    public void initStrategy(ApplicationContext applicationContext){
        initAuthHandler(applicationContext);
        initJWTProvider(applicationContext);
        initEventPublisher(applicationContext);
    }

    private void initAuthHandler(ApplicationContext applicationContext) {
        Map<String, AuthHandler> matchingBeans = applicationContext.getBeansOfType(AuthHandler.class);
        authHandlerList = new ArrayList<>(matchingBeans.values());
    }
    private void initJWTProvider(ApplicationContext applicationContext){
        jwtProvider = applicationContext.getBean(JWTProvider.class);
    }

    private void initEventPublisher(ApplicationContext applicationContext) {
        publisher = applicationContext;

    }
}
