package com.pawith.authapplication.application;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.authapplication.service.impl.JWTExtractEmailUseCaseImpl;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.authdomain.jwt.JWTProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@UnitTestConfig
@DisplayName("JWTExtractEmailService 테스트")
public class JWTExtractEmailUseCaseImplTest {

    @Mock
    private JWTProvider jwtProvider;
    JWTExtractEmailUseCaseImpl jwtExtractEmailUseCaseImpl;

    @BeforeEach
    void init() { jwtExtractEmailUseCaseImpl = new JWTExtractEmailUseCaseImpl(jwtProvider); }

    @Test
    @DisplayName("accessToken에서 email을 추출한다.")
    void extractEmail(){
        //given
        final String randomEmail = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        final String accessToken = jwtProvider.generateAccessToken(randomEmail);
        given(jwtProvider.extractEmailFromAccessToken(accessToken)).willReturn(randomEmail);
        //when
        final String email = jwtExtractEmailUseCaseImpl.extractEmail(accessToken);
        //then
        verify(jwtProvider).extractEmailFromAccessToken(accessToken);
        assertEquals(email, randomEmail);
    }
}
