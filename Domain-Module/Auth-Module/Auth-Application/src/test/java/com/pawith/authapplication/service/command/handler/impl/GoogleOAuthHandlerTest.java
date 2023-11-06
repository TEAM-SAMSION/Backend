package com.pawith.authapplication.service.command.handler.impl;

import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.service.command.feign.GoogleOAuthFeignClient;
import com.pawith.authapplication.service.command.feign.response.GoogleUserInfo;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.enums.Provider;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("GoogleOAuthHandler 테스트")
class GoogleOAuthHandlerTest {

    @Mock
    private GoogleOAuthFeignClient googleOAuthFeignClient;

    private GoogleOAuthHandler googleOAuthHandler;

    @BeforeEach
    void setUp() {
        googleOAuthHandler = new GoogleOAuthHandler(googleOAuthFeignClient);
    }

    @Test
    @DisplayName("isAccessible 테스트")
    void isAccessible() {
        // given
        OAuthRequest mockRequest = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(OAuthRequest.class)
            .set("provider", Provider.GOOGLE)
            .sample();
        // when
        final boolean result = googleOAuthHandler.isAccessible(mockRequest);
        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("handle 테스트")
    void handle() {
        // given
        OAuthRequest mockRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(OAuthRequest.class);
        GoogleUserInfo mockGoogleUserInfo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(GoogleUserInfo.class);
        given(googleOAuthFeignClient.getGoogleUserInfo("Bearer "+mockRequest.getAccessToken())).willReturn(mockGoogleUserInfo);
        // when
        final OAuthUserInfo result = googleOAuthHandler.handle(mockRequest);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getEmail()).isEqualTo(mockGoogleUserInfo.getEmail());
        Assertions.assertThat(result.getUsername()).isEqualTo(mockGoogleUserInfo.getName());
    }


}