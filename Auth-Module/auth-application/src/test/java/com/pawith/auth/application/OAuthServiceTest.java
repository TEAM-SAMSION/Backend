package com.pawith.auth.application;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.authmodule.application.dto.OAuthRequest;
import com.pawith.authmodule.application.dto.OAuthResponse;
import com.pawith.authmodule.application.dto.Provider;
import com.pawith.authmodule.application.port.out.command.OAuthInvoker;
import com.pawith.authmodule.application.service.OAuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
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
