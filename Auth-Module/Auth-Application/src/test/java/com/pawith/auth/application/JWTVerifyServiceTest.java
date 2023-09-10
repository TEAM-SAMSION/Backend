package com.pawith.auth.application;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.authmodule.application.service.impl.JWTVerifyService;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.jwt.jwt.JWTProvider;
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
    JWTVerifyService jwtVerifyService;

    @BeforeEach
    void init() { jwtVerifyService = new JWTVerifyService(jwtProvider); }

    @Test
    @DisplayName("토큰을 검증한다")
    void validateToken() {
        //given
        final String token = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        //when
        jwtVerifyService.validateToken(token);
        //then
        then(jwtProvider).should(times(1)).validateToken(token);
    }

}
