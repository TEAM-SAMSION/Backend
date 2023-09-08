package com.pawith.auth.application;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.authmodule.application.service.JWTExtractEmailService;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.jwt.JWTProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@UnitTestConfig
@DisplayName("JWTExtractEmailService 테스트")
public class JWTExtractEmailServiceTest {

    @Mock
    private JWTProvider jwtProvider;
    JWTExtractEmailService jwtExtractEmailService;

    @BeforeEach
    void init() { jwtExtractEmailService = new JWTExtractEmailService(jwtProvider); }

    @Test
    @DisplayName("accessToken에서 email을 추출한다.")
    void extractEmail(){
        //given
        final String randomEmail = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        final String accessToken = jwtProvider.generateAccessToken(randomEmail);
        given(jwtProvider.extractEmailFromAccessToken(accessToken)).willReturn(randomEmail);
        //when
        final String email = jwtExtractEmailService.extractEmail(accessToken);
        //then
        verify(jwtProvider).extractEmailFromAccessToken(accessToken);
        assertEquals(email, randomEmail);
    }
}
