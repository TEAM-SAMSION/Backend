package com.pawith.authapplication.service.oauth.handler.impl;

import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.service.oauth.feign.NaverOAuthFeignClient;
import com.pawith.authapplication.service.oauth.feign.response.NaverUserInfo;
import com.pawith.authapplication.service.oauth.impl.NaverOAuthHandler;
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
@DisplayName("NaverOAuthHandler 테스트")
class NaverOAuthHandlerTest {

    @Mock
    private NaverOAuthFeignClient naverOAuthFeignClient;

    private NaverOAuthHandler naverOAuthHandler;

    @BeforeEach
    void setUp() {
        naverOAuthHandler = new NaverOAuthHandler(naverOAuthFeignClient);
    }

    @Test
    @DisplayName("isAccessible 테스트")
    void isAccessible() {
        // given
        OAuthRequest mockRequest = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(OAuthRequest.class)
            .set("provider", Provider.NAVER)
            .sample();
        // when
        final boolean result = naverOAuthHandler.isAccessible(mockRequest);
        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("handle 테스트")
    void handle() {
        // given
        OAuthRequest mockRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(OAuthRequest.class);
        NaverUserInfo mockNaverUserInfo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(NaverUserInfo.class);
        given(naverOAuthFeignClient.getNaverUserInfo("Bearer "+mockRequest.getAccessToken())).willReturn(mockNaverUserInfo);
        // when
        final OAuthUserInfo result = naverOAuthHandler.handle(mockRequest);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getEmail()).isEqualTo(mockNaverUserInfo.getEmail());
        Assertions.assertThat(result.getUsername()).isEqualTo(mockNaverUserInfo.getNickname());
    }


}