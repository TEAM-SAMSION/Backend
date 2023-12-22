package com.pawith.authapplication.service;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.dto.TokenReissueResponse;
import com.pawith.authapplication.service.impl.ReissueUseCaseImpl;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.authdomain.service.TokenDeleteService;
import com.pawith.authdomain.service.TokenLockService;
import com.pawith.authdomain.service.TokenSaveService;
import com.pawith.authdomain.service.TokenValidateService;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@Slf4j
@UnitTestConfig
@DisplayName("ReissueUseCaseImpl 테스트")
class ReissueUseCaseImplTest {

    @Mock
    private JWTProvider jwtProvider;
    @Mock
    private TokenDeleteService tokenDeleteService;
    @Mock
    private TokenSaveService tokenSaveService;
    @Mock
    private TokenValidateService tokenValidateService;
    @Mock
    private TokenLockService tokenLockService;

    private ReissueUseCaseImpl reissueUseCase;

    @BeforeEach
    void setUp() {
        reissueUseCase = new ReissueUseCaseImpl(jwtProvider, tokenDeleteService,tokenSaveService, tokenValidateService, tokenLockService);
    }

    @Test
    @DisplayName("단일 캐싱된 토큰이 없을때는 새로운 토큰을 발급한다.")
    void reissue() {
        // given
        final String refreshToken = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final String email = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final JWTProvider.Token token = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(JWTProvider.Token.class);
        given(jwtProvider.existsCachedRefreshToken(refreshToken)).willReturn(false);
        given(jwtProvider.reIssueToken(refreshToken)).willReturn(token);
        given(jwtProvider.extractEmailFromToken(refreshToken, TokenType.REFRESH_TOKEN)).willReturn(email);
        // when
        TokenReissueResponse reissue = reissueUseCase.reissue(AuthConsts.AUTHENTICATION_TYPE_PREFIX + refreshToken);
        // then
        Assertions.assertThat(reissue.getAccessToken()).isEqualTo(AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.accessToken());
        Assertions.assertThat(reissue.getRefreshToken()).isEqualTo(AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.refreshToken());
        then(tokenValidateService).should().validateIsExistToken(refreshToken, TokenType.REFRESH_TOKEN);
        then(tokenDeleteService).should().deleteTokenByValue(refreshToken);
        then(tokenSaveService).should().saveToken(token.refreshToken(), email, TokenType.REFRESH_TOKEN);
        then(tokenLockService).should().lockToken(email);
        then(tokenLockService).should().releaseLockToken(email);
    }

    @Test
    @DisplayName("단일 캐싱된 토큰이 있을때는 캐싱된 토큰을 반환한다.")
    void reissue2() {
        // given
        final String refreshToken = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final String email = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final JWTProvider.Token token = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(JWTProvider.Token.class);
        given(jwtProvider.existsCachedRefreshToken(refreshToken)).willReturn(true);
        given(jwtProvider.getCachedToken(refreshToken)).willReturn(token);
        given(jwtProvider.extractEmailFromToken(refreshToken, TokenType.REFRESH_TOKEN)).willReturn(email);
        // when
        TokenReissueResponse reissue = reissueUseCase.reissue(AuthConsts.AUTHENTICATION_TYPE_PREFIX + refreshToken);
        // then
        Assertions.assertThat(reissue.getAccessToken()).isEqualTo(AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.accessToken());
        Assertions.assertThat(reissue.getRefreshToken()).isEqualTo(AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.refreshToken());
        then(tokenValidateService).shouldHaveNoInteractions();
        then(tokenDeleteService).shouldHaveNoInteractions();
        then(tokenSaveService).shouldHaveNoInteractions();
        then(tokenLockService).should().lockToken(email);
        then(tokenLockService).should().releaseLockToken(email);
    }

}