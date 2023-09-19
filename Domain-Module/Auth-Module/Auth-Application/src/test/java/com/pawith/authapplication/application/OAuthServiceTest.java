package com.pawith.authapplication.application;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.enums.Provider;
import com.pawith.authapplication.service.command.OAuthInvoker;
import com.pawith.authapplication.service.impl.OAuthService;
import com.pawith.commonmodule.UnitTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@UnitTestConfig
@DisplayName("OAuthService 테스트")
public class OAuthServiceTest {

    @Mock
    OAuthInvoker oAuthInvoker;
    OAuthService oAuthService;

    @BeforeEach
    void init() { oAuthService = new OAuthService(oAuthInvoker); }

    @Test
    @DisplayName("로그인을 수행한다.")
    void oAuthLogin() {
        //given
        final Provider testProvider = FixtureMonkey.create().giveMeOne(Provider.class);
        final String token = FixtureMonkey.create().giveMeOne(String.class);
        //when
        oAuthService.oAuthLogin(testProvider, token);
        //then
        then(oAuthInvoker).should(times(1)).execute(any());
    }
}
