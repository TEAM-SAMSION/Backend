package com.pawith.authmodule.application.port.out.observer.subject;

import com.pawith.authmodule.application.common.consts.AuthConsts;
import com.pawith.authmodule.application.dto.OAuthRequest;
import com.pawith.authmodule.application.dto.OAuthResponse;
import com.pawith.authmodule.application.dto.OAuthUserInfo;
import com.pawith.authmodule.application.port.out.observer.observer.AbstractOAuthObserver;
import com.pawith.commonmodule.observer.observer.Observer;
import com.pawith.commonmodule.observer.subject.Status;
import com.pawith.commonmodule.observer.subject.Subject;
import com.pawith.jwt.JWTProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public final class AbstractOAuthSubject implements Subject<OAuthRequest, OAuthResponse> {

    private static final String JWT_PROVIDER_BEAN_NAME = "JWTProvider";

    private final List<AbstractOAuthObserver> observerList = new ArrayList<>();

    private JWTProvider jwtProvider;

    @Override
    public void registerObserver(Observer<? extends Status,?> o) {
        observerList.add((AbstractOAuthObserver) o);
    }

    @Override
    public void removeObserver(Observer<? extends Status, ?> o) {
        observerList.remove((AbstractOAuthObserver) o);
    }

    @Override
    public OAuthResponse notifyObservers(OAuthRequest object) {
        final OAuthUserInfo oAuthUserInfo = attemptLogin(new OAuth(object.getProvider(), object.getAccessToken()));
        return generateServerAuthenticationTokens(oAuthUserInfo);
    }

    private OAuthUserInfo attemptLogin(OAuth oAuth) {
        for (AbstractOAuthObserver abstractOAuthObserver : observerList) {
            if (abstractOAuthObserver.isLogin(oAuth.getProvider())) {
                return abstractOAuthObserver.accept(oAuth);
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
        initOAuthObserver(applicationContext);
        initJWTProvider(applicationContext);
    }

    private void initOAuthObserver(ApplicationContext applicationContext) {
        applicationContext.getBeansOfType(AbstractOAuthObserver.class)
                .values()
                .forEach(this::registerObserver);
    }
    private void initJWTProvider(ApplicationContext applicationContext){
        jwtProvider = applicationContext.getBean(JWT_PROVIDER_BEAN_NAME, JWTProvider.class);
    }
}
