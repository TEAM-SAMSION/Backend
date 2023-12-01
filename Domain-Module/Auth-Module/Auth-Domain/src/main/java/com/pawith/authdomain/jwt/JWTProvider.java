package com.pawith.authdomain.jwt;

import com.pawith.authdomain.exception.AuthError;
import com.pawith.authdomain.jwt.exception.ExpiredTokenException;
import com.pawith.authdomain.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * jjwt 깃허브 : https://github.com/jwtk/jjwt
 */
@Component
@RequiredArgsConstructor
public class JWTProvider {
    private final JWTProperties jwtProperties;
    private final Map<String, Token> expiredTokenCacheMap = ExpiringMap.builder()
        .expiration(1, TimeUnit.MINUTES)
        .expirationPolicy(ExpirationPolicy.CREATED)
        .build();

    /**
     * access token 생성
     *
     * @param email 토큰에 담길 email
     * @return 생성된 access token
     */
    public String generateAccessToken(final String email) {
        final Claims claims = createPrivateClaims(email, TokenType.ACCESS_TOKEN);
        return generateToken(claims, jwtProperties.getAccessTokenExpirationTime());
    }

    /**
     * refershToken 생성
     * refreshToken은 DB에 저장함
     */
    public String generateRefreshToken(final String email) {
        final Claims claims = createPrivateClaims(email, TokenType.REFRESH_TOKEN);
        return generateToken(claims, jwtProperties.getRefreshTokenExpirationTime());
    }

    /**
     * refresh token을 이용하여 access token 재발급
     *
     * @throws InvalidTokenException : 잘못된 토큰 요청시 발생
     * @throws ExpiredTokenException : 만료된 토큰 요청시 발생
     */
    public String reIssueAccessToken(final String refreshToken) {
        validateToken(refreshToken, TokenType.REFRESH_TOKEN);
        final String email = extractEmailFromToken(refreshToken, TokenType.REFRESH_TOKEN);
        return generateAccessToken(email);
    }

    public Token reIssueToken(final String refreshToken){
        validateToken(refreshToken, TokenType.REFRESH_TOKEN);
        final String email = extractEmailFromToken(refreshToken, TokenType.REFRESH_TOKEN);
        final String newAccessToken = generateAccessToken(email);
        final String newRefreshToken = generateRefreshToken(email);

        expiredTokenCacheMap.put(refreshToken, new Token(newAccessToken, newRefreshToken));

        return new Token(newAccessToken, newRefreshToken);
    }

    /**
     * refresh token을 이용하여 refresh token 재발급
     *
     * @throws InvalidTokenException : 잘못된 토큰 요청시 발생
     * @TODO : RefreshToken 인증 범위 결정
     */
    public String reIssueRefreshToken(final String refreshToken) {
        validateToken(refreshToken, TokenType.REFRESH_TOKEN);
        final String email = extractEmailFromToken(refreshToken, TokenType.REFRESH_TOKEN);
        return generateRefreshToken(email);
    }

    public String extractEmailFromToken(String token, TokenType tokenType) {
        return initializeJwtParser(tokenType)
            .parseClaimsJws(token)
            .getBody()
            .get(JWTConsts.EMAIL)
            .toString();
    }

    public boolean existsCachedRefreshToken(String refreshToken){
        return expiredTokenCacheMap.containsKey(refreshToken);
    }

    public Token getCachedToken(String refreshToken){
        return expiredTokenCacheMap.get(refreshToken);
    }

    /**
     * 기본 jwt claims 스팩에서 개발자가 더 추가한 claim을 private claim이라고 함
     * <br>private claims : email, tokenType + issuer
     *
     * @param email     토큰에 담길 email
     * @param tokenType 토큰 타입(ACCESS_TOKEN, REFRESH_TOKEN)
     */
    private Claims createPrivateClaims(final String email, final TokenType tokenType) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put(JWTConsts.EMAIL, email);
        claims.put(JWTConsts.TOKEN_TYPE, tokenType.name());
        return Jwts.claims(claims)
            .setIssuer(JWTConsts.TOKEN_ISSUER);
    }

    /**
     * 토큰 생성 메소드
     *
     * @param claims         토큰에 담길 정보(email, tokenType)
     * @param expirationTime 토큰 만료 시간(시간 단위 ms)
     * @return 생성된 토큰
     */
    private String generateToken(final Claims claims, final Long expirationTime) {
        final Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expirationTime))
            .signWith(getSigningKey())
            .compact();
    }

    /**
     * @return 서명에 사용할 Key 반환
     */
    private Key getSigningKey() {
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

    /**
     * iss, key 설정
     *
     * @return 토큰 검증 위한 JwtParser 반환
     */
    private JwtParser initializeJwtParser(final TokenType tokenType) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .requireIssuer(JWTConsts.TOKEN_ISSUER)
            .require(JWTConsts.TOKEN_TYPE, tokenType.name())
            .build();
    }

    public record Token(String accessToken, String refreshToken){
    }

}
