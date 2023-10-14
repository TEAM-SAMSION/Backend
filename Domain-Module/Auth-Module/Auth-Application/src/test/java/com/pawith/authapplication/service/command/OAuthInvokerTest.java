package com.pawith.authapplication.service.command;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.service.command.handler.AuthHandler;
import com.pawith.authapplication.utils.TokenExtractUtils;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@Slf4j
@UnitTestConfig
@DisplayName("OAuthInvoker 테스트")
class OAuthInvokerTest {

    @Mock
    private JWTProvider jwtProvider;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @Spy
    private List<AuthHandler> authHandlerList = new ArrayList<>();
    @Mock
    private AuthHandler authHandler;

    private OAuthInvoker oAuthInvoker;

    @BeforeEach
    void setUp() {
        authHandlerList.add(authHandler);
        oAuthInvoker=new OAuthInvoker(authHandlerList, jwtProvider, applicationEventPublisher);
    }

    @Test
    @DisplayName("execute 테스트")
    void execute(){
        // given
        final OAuthRequest mockRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(OAuthRequest.class);
        final OAuthUserInfo mockOAuthUserInfo = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(OAuthUserInfo.class);
        final String accessToken = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final String refreshToken = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        given(authHandler.isAccessible(mockRequest)).willReturn(true);
        given(authHandler.handle(mockRequest)).willReturn(mockOAuthUserInfo);
        given(jwtProvider.generateAccessToken(mockOAuthUserInfo.getEmail())).willReturn(accessToken);
        given(jwtProvider.generateRefreshToken(mockOAuthUserInfo.getEmail())).willReturn(refreshToken);
        // when
        final OAuthResponse result = oAuthInvoker.execute(mockRequest);
        // then
        Assertions.assertThat(result.getAccessToken()).startsWith(AuthConsts.AUTHENTICATION_TYPE);
        Assertions.assertThat(result.getRefreshToken()).startsWith(AuthConsts.AUTHENTICATION_TYPE);
        Assertions.assertThat(TokenExtractUtils.extractToken(result.getAccessToken())).isEqualTo(accessToken);
        Assertions.assertThat(TokenExtractUtils.extractToken(result.getRefreshToken())).isEqualTo(refreshToken);
    }

}