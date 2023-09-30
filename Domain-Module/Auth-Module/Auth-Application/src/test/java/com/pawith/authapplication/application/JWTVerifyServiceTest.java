package com.pawith.authapplication.application;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.authapplication.service.impl.JWTVerifyUseCaseImpl;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.authdomain.jwt.JWTProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@UnitTestConfig
@DisplayName("JWTVerifyService 테스트")
public class JWTVerifyServiceTest {

    @Mock
    JWTProvider jwtProvider;
    JWTVerifyUseCaseImpl jwtVerifyUseCaseImpl;

    @BeforeEach
    void init() { jwtVerifyUseCaseImpl = new JWTVerifyUseCaseImpl(jwtProvider); }

    @Test
    @DisplayName("토큰을 검증한다")
    void validateToken() {
        //given
        final String token = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        //when
        jwtVerifyUseCaseImpl.validateToken(token);
        //then
        then(jwtProvider).should(times(1)).validateToken(token);
    }

}
