package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.dto.TokenReissueResponse;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.authdomain.service.TokenDeleteService;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;
@Slf4j
@UnitTestConfig
@DisplayName("ReissueUseCaseImpl 테스트")
class ReissueUseCaseImplTest {

    @Mock
    private JWTProvider jwtProvider;
    @Mock
    private TokenDeleteService tokenDeleteService;

    private ReissueUseCaseImpl reissueUseCase;

    @BeforeEach
    void setUp() {
        reissueUseCase = new ReissueUseCaseImpl(jwtProvider, tokenDeleteService);
    }

    @Test
    @DisplayName("reissue 테스트")
    void reissue() {
        // given
        final String refreshToken = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final String reissueAccessToken = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final String reissueRefreshToken = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        given(jwtProvider.reIssueAccessToken(refreshToken)).willReturn(reissueAccessToken);
        given(jwtProvider.reIssueRefreshToken(refreshToken)).willReturn(reissueRefreshToken);
        // when
        TokenReissueResponse reissue = reissueUseCase.reissue(AuthConsts.AUTHENTICATION_TYPE_PREFIX + refreshToken);
        // then
        Assertions.assertThat(reissue.getAccessToken()).isEqualTo(AuthConsts.AUTHENTICATION_TYPE_PREFIX + reissueAccessToken);
        Assertions.assertThat(reissue.getRefreshToken()).isEqualTo(AuthConsts.AUTHENTICATION_TYPE_PREFIX + reissueRefreshToken);
    }

}