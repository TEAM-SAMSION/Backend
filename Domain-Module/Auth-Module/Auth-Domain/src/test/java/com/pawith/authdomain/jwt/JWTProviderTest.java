package com.pawith.authdomain.jwt;

import com.pawith.authdomain.jwt.exception.ExpiredTokenException;
import com.pawith.authdomain.jwt.exception.InvalidTokenException;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.cache.operators.ValueOperator;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

@UnitTestConfig
@DisplayName("JWTProvider 테스트")
public class JWTProviderTest {
    private final JWTProperties jwtProperties = new JWTProperties(JWTTestConsts.SECRET, JWTTestConsts.ACCESS_TOKEN_EXPIRED_TIME, JWTTestConsts.REFRESH_TOKEN_EXPIRED_TIME);

    private JWTProvider jwtProvider;

    @Mock
    private ValueOperator<String, JWTProvider.Token> tokenValueOperator;

    @BeforeEach
    void init(){
        jwtProvider = new JWTProvider(jwtProperties,tokenValueOperator);
    }

    @Test
    @DisplayName("사용자 정보가 입력으로 들어오면 accessToken을 발급하여 반환한다.")
    void generateAccessTokenByEmail(){
        //given
        final PrivateClaims.UserClaims userClaims = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(PrivateClaims.UserClaims.class);
        final String accessToken = jwtProvider.generateAccessToken(userClaims);
        //when
        final Claims body = extractClaimsFromToken(accessToken);
        //then
        Assertions.assertThat(body.get(JWTConsts.USER_CLAIMS)).usingRecursiveComparison().isEqualTo(userClaims);
        Assertions.assertThat(body.get(JWTConsts.TOKEN_TYPE)).isEqualTo(TokenType.ACCESS_TOKEN);
    }

    @Test
    @DisplayName("사용자 정보가 입력으로 들어오면 refreshToken을 발급하여 반환한다.")
    void generateRefreshTokenByEmail(){
        //given
        final PrivateClaims.UserClaims userClaims = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(PrivateClaims.UserClaims.class);
        final String refreshToken = jwtProvider.generateRefreshToken(userClaims);
        //when
        final Claims body = extractClaimsFromToken(refreshToken);
        //then
        Assertions.assertThat(body.get(JWTConsts.USER_CLAIMS)).usingRecursiveComparison().isEqualTo(userClaims);
        Assertions.assertThat(body.get(JWTConsts.TOKEN_TYPE)).isEqualTo(TokenType.REFRESH_TOKEN);
    }

    @Test
    @DisplayName("validateToken 메소드에 잘못된 token이 입력되면 InvalidTokenException 예외가 발생한다.")
    void validateTokenWithInvalidToken(){
        //given
        final String randomToken = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(String.class);
        //when
        //then
        Assertions.assertThatCode(() -> jwtProvider.validateToken(randomToken, TokenType.ACCESS_TOKEN))
            .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    @DisplayName("validateToken 메소드에 만료된 token이 입력되면 ExpiredTokenException 예외가 발생한다.")
    @SneakyThrows
    void validateTokenWithExpiredToken(){
        //given
        final PrivateClaims.UserClaims userClaims = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(PrivateClaims.UserClaims.class);
        final String refreshToken = jwtProvider.generateAccessToken(userClaims);
        Thread.sleep(JWTTestConsts.REFRESH_TOKEN_EXPIRED_TIME+1);
        //when
        //then
        Assertions.assertThatCode(() -> jwtProvider.validateToken(refreshToken, TokenType.REFRESH_TOKEN))
            .isInstanceOf(ExpiredTokenException.class);
    }


    private Claims extractClaimsFromToken(final String token){
        return createJwtParser()
            .parseSignedClaims(token)
            .getPayload();

    }

    private JwtParser createJwtParser() {
        return Jwts.parser()
            .json(new JacksonDeserializer<>(PrivateClaims.getClaimsTypeDetailMap()))
            .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret())))
            .requireIssuer(JWTConsts.TOKEN_ISSUER)
            .build();
    }
}
