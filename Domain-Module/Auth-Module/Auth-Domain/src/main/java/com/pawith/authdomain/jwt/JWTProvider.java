package com.pawith.authdomain.jwt;

import com.pawith.authdomain.exception.AuthError;
import com.pawith.authdomain.jwt.exception.ExpiredTokenException;
import com.pawith.authdomain.jwt.exception.InvalidTokenException;
import com.pawith.commonmodule.cache.operators.ValueOperator;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * jjwt 깃허브 : https://github.com/jwtk/jjwt
 */
@Component
@RequiredArgsConstructor
public class JWTProvider {
    private final JWTProperties jwtProperties;
    private final ValueOperator<String, Token> tokenCacheOperator;

    public String generateAccessToken(final PrivateClaims.UserClaims userClaims) {
        return generateToken(userClaims.createPrivateClaims(TokenType.ACCESS_TOKEN), jwtProperties.getAccessTokenExpirationTime());
    }

    public String generateRefreshToken(final PrivateClaims.UserClaims userClaims) {
        return generateToken(userClaims.createPrivateClaims(TokenType.REFRESH_TOKEN), jwtProperties.getRefreshTokenExpirationTime());
    }

    public Token generateToken(final PrivateClaims.UserClaims userClaims) {
        final String accessToken = generateAccessToken(userClaims);
        final String refreshToken = generateRefreshToken(userClaims);
        return new Token(accessToken, refreshToken);
    }

    public Token reIssueToken(final String refreshToken) {
        validateToken(refreshToken, TokenType.REFRESH_TOKEN);
        final PrivateClaims.UserClaims userClaims = extractUserClaimsFromToken(refreshToken, TokenType.REFRESH_TOKEN);
        final String newAccessToken = generateAccessToken(userClaims);
        final String newRefreshToken = generateRefreshToken(userClaims);

        tokenCacheOperator.setWithExpire(refreshToken, new Token(newAccessToken, newRefreshToken), 1, TimeUnit.MINUTES);

        return new Token(newAccessToken, newRefreshToken);
    }

    public PrivateClaims.UserClaims extractUserClaimsFromToken(String token, TokenType tokenType) {
        return initializeJwtParser(tokenType)
            .parseSignedClaims(token)
            .getPayload()
            .get(JWTConsts.USER_CLAIMS, PrivateClaims.UserClaims.class);
    }

    public boolean existsCachedRefreshToken(String refreshToken) {
        return tokenCacheOperator.contains(refreshToken);
    }

    public Token getCachedToken(String refreshToken) {
        return tokenCacheOperator.get(refreshToken);
    }

    private String generateToken(final PrivateClaims privateClaims, final Long expirationTime) {
        final Date now = new Date();
        return Jwts.builder()
            .issuer(JWTConsts.TOKEN_ISSUER)
            .claims(privateClaims.createClaimsMap())
            .issuedAt(now)
            .expiration(new Date(now.getTime() + expirationTime))
            .signWith(getSigningKey())
            .compact();
    }

    /**
     * @return 서명에 사용할 Key 반환
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }

    /**
     * @throws InvalidTokenException 잘못된 토큰이 요청되었을 때 반환(서명 오류, 잘못된 토큰 형식, 잘못된 토큰 발급자, null이거나 공백인 경우)
     * @throws ExpiredTokenException 만료된 토큰이 요청되었을 때 반환
     */
    public void validateToken(final String token, final TokenType tokenType) {
        final JwtParser jwtParser = initializeJwtParser(tokenType);
        try {
            jwtParser.parse(token);
        } catch (MalformedJwtException | SignatureException | IncorrectClaimException | IllegalArgumentException e) {
            throw new InvalidTokenException(AuthError.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException(AuthError.EXPIRED_TOKEN);
        }
    }


    private JwtParser initializeJwtParser(final TokenType tokenType) {
        return Jwts.parser()
            .json(new JacksonDeserializer<>(PrivateClaims.getClaimsTypeDetailMap()))
            .verifyWith(getSigningKey())
            .requireIssuer(JWTConsts.TOKEN_ISSUER)
            .require(JWTConsts.TOKEN_TYPE, tokenType)
            .build();
    }

    public record Token(String accessToken, String refreshToken) {
    }

}
