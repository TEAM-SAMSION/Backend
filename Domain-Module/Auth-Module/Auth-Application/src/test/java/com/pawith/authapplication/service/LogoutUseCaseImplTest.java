package com.pawith.authapplication.service;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.service.impl.LogoutUseCaseImpl;
import com.pawith.authdomain.entity.Token;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.authdomain.service.TokenDeleteService;
import com.pawith.authdomain.service.TokenQueryService;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@UnitTestConfig
@DisplayName("LogoutUseCaseImpl 테스트")
class LogoutUseCaseImplTest {

    @Mock
    private TokenDeleteService tokenDeleteService;
    @Mock
    private TokenQueryService tokenQueryService;

    private LogoutUseCaseImpl logoutUseCase;

    @BeforeEach
    void setUp() {
        logoutUseCase = new LogoutUseCaseImpl(tokenDeleteService, tokenQueryService);
    }

    @Test
    @DisplayName("logoutAccessUser 테스트")
    void logoutAccessUser() {
        // given
        final String refreshToken = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final String refreshTokenHeader = AuthConsts.AUTHENTICATION_TYPE_PREFIX+ refreshToken;
        final Token mockToken = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Token.class)
            .set("value", refreshToken)
            .sample();
        given(tokenQueryService.findTokenByValue(refreshToken, TokenType.REFRESH_TOKEN)).willReturn(mockToken);
        // when
        logoutUseCase.logoutAccessUser(refreshTokenHeader);
        // then
        then(tokenDeleteService).should().deleteToken(mockToken);
    }
}