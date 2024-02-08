package com.pawith.authapplication.service;

import com.pawith.authapplication.service.impl.JWTExtractUserDetailsUseCaseImpl;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.authdomain.jwt.PrivateClaims;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@UnitTestConfig
@DisplayName("JWTExtractEmailService 테스트")
public class JWTExtractUserDetailsUseCaseImplTest {

    @Mock
    private JWTProvider jwtProvider;
    JWTExtractUserDetailsUseCaseImpl jwtExtractEmailUseCaseImpl;

    @BeforeEach
    void init() { jwtExtractEmailUseCaseImpl = new JWTExtractUserDetailsUseCaseImpl(jwtProvider); }

    @Test
    @DisplayName("accessToken에서 사용자 정보를 추출한다.")
    void extractEmail(){
        //given
        final PrivateClaims.UserClaims userClaims = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(PrivateClaims.UserClaims.class);
        final String accessToken = jwtProvider.generateAccessToken(userClaims);
        given(jwtProvider.extractUserClaimsFromToken(accessToken, TokenType.ACCESS_TOKEN)).willReturn(userClaims);
        //when
        final Long userId = jwtExtractEmailUseCaseImpl.extract(accessToken);
        //then
        verify(jwtProvider).extractUserClaimsFromToken(accessToken, TokenType.ACCESS_TOKEN);
        Assertions.assertThat(userId).isEqualTo(userClaims.getUserId());
    }
}
