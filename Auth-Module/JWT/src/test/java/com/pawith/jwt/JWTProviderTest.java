package com.pawith.jwt;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.cache.ObjectRegistry;
import com.pawith.jwt.exception.ExpiredTokenException;
import com.pawith.jwt.exception.InvalidTokenException;
import com.pawith.jwt.exception.NotExistTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("JWTProvider 테스트")
public class JWTProviderTest {
    private final JWTProperties jwtProperties = new JWTProperties(JWTTestConsts.SECRET, JWTTestConsts.ACCESS_TOKEN_EXPIRED_TIME, JWTTestConsts.REFRESH_TOKEN_EXPIRED_TIME);
    @Mock
    private ObjectRegistry objectRegistry;

    private JWTProvider jwtProvider;

    @BeforeEach
    void init(){
        jwtProvider = new JWTProvider(jwtProperties, objectRegistry);
    }

    @Test
    @DisplayName("사용자 email이 입력으로 들어오면 accessToken을 발급하여 반환한다.")
    void generateAccessTokenByEmail(){
        //given
        final String randomEmail = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        final String accessToken = jwtProvider.generateAccessToken(randomEmail);
        //when
        final Claims body = extractClaimsFromToken(accessToken);
        //then
        Assertions.assertThat(body.get(JWTConsts.EMAIL)).isEqualTo(randomEmail);
        Assertions.assertThat(body.get(JWTConsts.TOKEN_TYPE)).isEqualTo(TokenType.ACCESS_TOKEN.toString());
    }

    @Test
    @DisplayName("사용자 email이 입력으로 들어오면 refreshToken을 발급하여 반환한다.")
    void generateRefreshTokenByEmail(){
        //given
        final String randomEmail = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        final String refreshToken = jwtProvider.generateRefreshToken(randomEmail);
        //when
        final Claims body = extractClaimsFromToken(refreshToken);
        //then
        Assertions.assertThat(body.get(JWTConsts.EMAIL)).isEqualTo(randomEmail);
        Assertions.assertThat(body.get(JWTConsts.TOKEN_TYPE)).isEqualTo(TokenType.REFRESH_TOKEN.toString());
    }

    @Test
    @DisplayName("refreshToken을 이용하여 accessToken을 재발급한다.")
    void reIssueAccessToken(){
        //given
        final String randomEmail = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        final String refreshToken = jwtProvider.generateRefreshToken(randomEmail);
        given(objectRegistry.getKeyFromValue(refreshToken)).willReturn(Optional.of(randomEmail));
        //when
        final String accessToken = jwtProvider.reIssueAccessToken(refreshToken);
        final Claims claims = extractClaimsFromToken(accessToken);
        //then
        Assertions.assertThat(accessToken).isNotNull();
        Assertions.assertThat(claims.get(JWTConsts.EMAIL)).isEqualTo(randomEmail);
        Assertions.assertThat(claims.get(JWTConsts.TOKEN_TYPE)).isEqualTo(TokenType.ACCESS_TOKEN.toString());
    }

    @Test
    @DisplayName("refreshToken을 이용하여 refreshToken을 재발급한다.")
    void reIssueRefreshToken(){
        //given
        final String randomEmail = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        final String refreshToken = jwtProvider.generateRefreshToken(randomEmail);
        given(objectRegistry.getKeyFromValue(refreshToken)).willReturn(Optional.of(randomEmail));
        //when
        final String newRefreshToken = jwtProvider.reIssueRefreshToken(refreshToken);
        final Claims claims = extractClaimsFromToken(newRefreshToken);
        //then
        Assertions.assertThat(newRefreshToken).isNotNull();
        Assertions.assertThat(claims.get(JWTConsts.EMAIL)).isEqualTo(randomEmail);
        Assertions.assertThat(claims.get(JWTConsts.TOKEN_TYPE)).isEqualTo(TokenType.REFRESH_TOKEN.toString());
    }

    @Test
    @DisplayName("refreshToken이 만료되었을 때, accessToken 재발급시 예외가 발생한다.")
    @SneakyThrows
    void reIssueAccessTokenWithExpiredRefreshToken(){
        //given
        final String randomEmail = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        final String refreshToken = jwtProvider.generateRefreshToken(randomEmail);
        Thread.sleep(JWTTestConsts.REFRESH_TOKEN_EXPIRED_TIME+1);
        //when
        //then
        Assertions.assertThatCode(() -> jwtProvider.reIssueAccessToken(refreshToken))
            .isInstanceOf(ExpiredTokenException.class);
    }

    @Test
    @DisplayName("refreshToken이 만료되었을 때, refreshToken 재발급시 예외가 발생하지 않는다.")
    @SneakyThrows
    void reIssueRefreshTokenWithExpiredRefreshToken(){
        //given
        final String randomEmail = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        final String refreshToken = jwtProvider.generateRefreshToken(randomEmail);
        given(objectRegistry.getKeyFromValue(refreshToken)).willReturn(Optional.of(randomEmail));
        Thread.sleep(JWTTestConsts.REFRESH_TOKEN_EXPIRED_TIME+1);
        //when
        //then
        Assertions.assertThatCode(() -> jwtProvider.reIssueRefreshToken(refreshToken))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("accessToken에서 email을 추출한다.")
    void extractEmailFromAccessToken(){
        //given
        final String randomEmail = FixtureMonkey.create().giveMe(String.class).findAny().get();
        final String accessToken = jwtProvider.generateAccessToken(randomEmail);
        //when
        final String email = jwtProvider.extractEmailFromAccessToken(accessToken);
        //then
        Assertions.assertThat(email).isEqualTo(randomEmail);
    }

    @Test
    @DisplayName("refreshToken에서 email을 추출할때 잘못된 토큰이면 NotExistTokenException 예외가 발생한다.")
    void extractEmailFromRefreshToken(){
        //given
        final String randomEmail = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        final String refreshToken = jwtProvider.generateRefreshToken(randomEmail);
        //when
        //then
        Assertions.assertThatCode(() -> jwtProvider.reIssueRefreshToken(refreshToken))
            .isInstanceOf(NotExistTokenException.class);
    }

    @Test
    @DisplayName("validateToken 메소드에 잘못된 token이 입력되면 InvalidTokenException 예외가 발생한다.")
    void validateTokenWithInvalidToken(){
        //given
        final String randomToken = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        //when
        //then
        Assertions.assertThatCode(() -> jwtProvider.validateToken(randomToken))
            .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    @DisplayName("validateToken 메소드에 만료된 token이 입력되면 ExpiredTokenException 예외가 발생한다.")
    @SneakyThrows
    void validateTokenWithExpiredToken(){
        //given
        final String randomEmail = FixtureMonkey.create().giveMe(String.class).findFirst().get();
        final String refreshToken = jwtProvider.generateRefreshToken(randomEmail);
        Thread.sleep(JWTTestConsts.REFRESH_TOKEN_EXPIRED_TIME+1);
        //when
        //then
        Assertions.assertThatCode(() -> jwtProvider.validateToken(refreshToken))
            .isInstanceOf(ExpiredTokenException.class);
    }


    private Claims extractClaimsFromToken(final String token){
        return createJwtParser()
            .parseClaimsJws(token)
            .getBody();
    }

    private JwtParser createJwtParser() {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret())))
            .requireIssuer(JWTConsts.TOKEN_ISSUER)
            .build();
    }
}
