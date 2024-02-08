package com.pawith.authapplication.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.enums.Provider;
import com.pawith.authapplication.service.oauth.OAuthInvoker;
import com.pawith.authapplication.service.impl.OAuthUseCaseImpl;
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
public class OAuthUseCaseImplTest {

    @Mock
    OAuthInvoker oAuthInvoker;
    OAuthUseCaseImpl oAuthUseCaseImpl;

    @BeforeEach
    void init() { oAuthUseCaseImpl = new OAuthUseCaseImpl(oAuthInvoker); }

    @Test
    @DisplayName("로그인을 수행한다.")
    void oAuthLogin() {
        //given
        final Provider testProvider = FixtureMonkey.create().giveMeOne(Provider.class);
        final String accessToken = FixtureMonkey.create().giveMeOne(String.class);
        final String refreshToken = FixtureMonkey.create().giveMeOne(String.class);
        //when
        oAuthUseCaseImpl.oAuthLogin(testProvider, accessToken, refreshToken);
        //then
        then(oAuthInvoker).should(times(1)).execute(any());
    }
}
