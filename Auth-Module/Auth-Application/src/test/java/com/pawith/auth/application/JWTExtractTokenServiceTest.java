package com.pawith.auth.application;

import com.pawith.authmodule.application.common.exception.InvalidAuthorizationTypeException;
import com.pawith.authmodule.application.service.JWTExtractTokenService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("JWTExtractTokenService 테스트")
public class JWTExtractTokenServiceTest {

    JWTExtractTokenService jwtExtractTokenService;

    @BeforeEach
    void init() { jwtExtractTokenService = new JWTExtractTokenService(); }

    private static String TOKEN_HEADER = "Bearer access_token";
    private static String WRONG_TOKEN_HEADER = "Wrong access_token";
    private static String ACCESS_TOKEN = "access_token";

    @Test
    @DisplayName("토큰을 추출한다. AUTHENTICATION_TYPE와 동일하면 정상작동한다")
    void extractToken() {
        //given
        //when
        final String extractedToken = jwtExtractTokenService.extractToken(TOKEN_HEADER);
        //then
        assertEquals(ACCESS_TOKEN,extractedToken);
    }

    @Test
    @DisplayName("토큰을 추출한다. AUTHENTICATION_TYPE와 다르면 예외가 발생한다")
    void extractTokenWithAuthorizationType() {
        //given
        //when
        //then
        Assertions.assertThatCode(() -> jwtExtractTokenService.extractToken(WRONG_TOKEN_HEADER))
                .isInstanceOf(InvalidAuthorizationTypeException.class);
    }
}