package com.pawith.authapplication.service.oauth.handler.impl;

import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.service.oauth.feign.KakaoOAuthFeignClient;
import com.pawith.authapplication.service.oauth.feign.response.KakaoUserInfo;
import com.pawith.authapplication.service.oauth.feign.response.TokenInfo;
import com.pawith.authapplication.service.oauth.impl.KakaoOAuthHandler;
import com.pawith.authdomain.jwt.exception.InvalidTokenException;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.enums.Provider;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@Slf4j
@UnitTestConfig
@DisplayName("KakaoOAuthHandler 테스트")
public class KakaoOAuthHandlerTest {

    @Mock
    private KakaoOAuthFeignClient kakaoOAuthFeignClient;

    private KakaoOAuthHandler kakaoOAuthHandler;

    @BeforeEach
    void setUp() {
        kakaoOAuthHandler = new KakaoOAuthHandler(kakaoOAuthFeignClient);
        ReflectionTestUtils.setField(kakaoOAuthHandler, "appId", "testAppId");
    }

    @Test
    @DisplayName("isAccessible 테스트")
    void isAccessible() {
        // given
        OAuthRequest mockRequest = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(OAuthRequest.class)
                .set("provider", Provider.KAKAO)
                .sample();
        log.info("mockRequest: {}", mockRequest.getProvider());
        // when
        final boolean result = kakaoOAuthHandler.isAccessible(mockRequest);
        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("handle 테스트")
    void handle() {
        // given
        OAuthRequest mockRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(OAuthRequest.class);
        TokenInfo mockTokenInfo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(TokenInfo.class)
                .set("appId", "testAppId")
                .sample();
        KakaoUserInfo mockKakaoUserInfo = getKakaoUserInfo();
        given(kakaoOAuthFeignClient.getKakaoTokenInfo("Bearer " + mockRequest.getAccessToken())).willReturn(mockTokenInfo);
        given(kakaoOAuthFeignClient.getKakaoUserInfo("Bearer " + mockRequest.getAccessToken())).willReturn(mockKakaoUserInfo);
        // when
        final OAuthUserInfo result = kakaoOAuthHandler.handle(mockRequest);
        // then
         Assertions.assertThat(result).isNotNull();
         Assertions.assertThat(result.getEmail()).isEqualTo(mockKakaoUserInfo.getEmail());
         Assertions.assertThat(result.getUsername()).isEqualTo(mockKakaoUserInfo.getNickname());
    }

    @Test
    @DisplayName("handle 테스트 - 앱 ID가 다르면 InvalidTokenException 이 발생합니다.")
    void handleWhenAppIdIsDifferent() {
        // given
        OAuthRequest mockRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(OAuthRequest.class);
        TokenInfo mockTokenInfo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(TokenInfo.class)
                .set("appId", "anotherTestAppId")
                .sample();
        given(kakaoOAuthFeignClient.getKakaoTokenInfo("Bearer " + mockRequest.getAccessToken())).willReturn(mockTokenInfo);
        // when
        // then
        Assertions.assertThatCode(() -> kakaoOAuthHandler.handle(mockRequest))
                .isInstanceOf(InvalidTokenException.class);
    }

    private KakaoUserInfo getKakaoUserInfo() {
        Map<String, Object> kakaoAccount = new HashMap<>();

        Map<String, Object> profile = new HashMap<>();
        profile.put("nickname", "testNickname");

        kakaoAccount.put("profile", profile);
        kakaoAccount.put("email", "testEmail");

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(kakaoAccount);
        return kakaoUserInfo;
    }

}