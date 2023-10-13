package com.pawith.authapplication.service;

import com.pawith.authapplication.exception.InvalidAuthorizationTypeException;
import com.pawith.authapplication.service.impl.JWTExtractTokenUseCaseImpl;
import com.pawith.commonmodule.UnitTestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTestConfig
@DisplayName("JWTExtractTokenService 테스트")
public class JWTExtractTokenUseCaseImplTest {

    JWTExtractTokenUseCaseImpl jwtExtractTokenUseCaseImpl;

    @BeforeEach
    void init() { jwtExtractTokenUseCaseImpl = new JWTExtractTokenUseCaseImpl(); }

    private static String TOKEN_HEADER = "Bearer access_token";
    private static String WRONG_TOKEN_HEADER = "Wrong access_token";
    private static String ACCESS_TOKEN = "access_token";

    @Test
    @DisplayName("토큰을 추출한다. AUTHENTICATION_TYPE와 동일하면 정상작동한다")
    void extractToken() {
        //given
        //when
        final String extractedToken = jwtExtractTokenUseCaseImpl.extractToken(TOKEN_HEADER);
        //then
        assertEquals(ACCESS_TOKEN,extractedToken);
    }

    @Test
    @DisplayName("토큰을 추출한다. AUTHENTICATION_TYPE와 다르면 예외가 발생한다")
    void extractTokenWithAuthorizationType() {
        //given
        //when
        //then
        Assertions.assertThatCode(() -> jwtExtractTokenUseCaseImpl.extractToken(WRONG_TOKEN_HEADER))
                .isInstanceOf(InvalidAuthorizationTypeException.class);
    }
}
