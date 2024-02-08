package com.pawith.authapplication.service.oauth.impl;

import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.service.oauth.feign.AppleFeignClient;
import com.pawith.authapplication.service.oauth.feign.response.Keys;
import com.pawith.authapplication.service.command.handler.AuthHandler;
import com.pawith.authdomain.exception.AuthError;
import com.pawith.authdomain.jwt.exception.InvalidTokenException;
import com.pawith.commonmodule.enums.Provider;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import static org.springframework.security.oauth2.jwt.JoseHeaderNames.KID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleOAuthHandler implements AuthHandler {
    private static final Provider OAUTH_TYPE = Provider.APPLE;
    private static final String APPLE_USER_INFO = "email";
    private static final String APPLE_ISS = "https://appleid.apple.com";

    private final AppleFeignClient appleFeignClient;
    @Value("${app-id.apple}")
    private String apple_aud;

    @Override
    public OAuthUserInfo handle(OAuthRequest authenticationInfo) {
        log.info("AppleOAuthObserver attemptLogin");
        // 토큰 서명 검증
        Jws<Claims> oidcTokenJws = sigVerificationAndGetJws(authenticationInfo.getAccessToken());
        // 토큰 바디 파싱해서 사용자 정보 획득
        String email = (String) oidcTokenJws.getBody().get(APPLE_USER_INFO);
        return new OAuthUserInfo("포잇", email);
    }

    @Override
    public boolean isAccessible(OAuthRequest authenticationInfo) {
        return OAUTH_TYPE.equals(authenticationInfo.getProvider());
    }


    private Jws<Claims> sigVerificationAndGetJws(String unverifiedToken) {
        String kid = getKidFromUnsignedTokenHeader(unverifiedToken, APPLE_ISS, apple_aud);

        Keys keys = appleFeignClient.getKeys();
        Keys.PubKey pubKey = keys.getKeys().stream()
            .filter((key) -> key.getKid().equals(kid))
            .findAny()
            .get();

        return getOIDCTokenJws(unverifiedToken, pubKey.getN(), pubKey.getE());
    }

    public Jws<Claims> getOIDCTokenJws(String token, String modulus, String exponent) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(getRSAPublicKey(modulus, exponent))
                .build()
                .parseClaimsJws(token);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new InvalidTokenException(AuthError.INVALID_TOKEN);
        }
    }

    private Key getRSAPublicKey(String modulus, String exponent)
        throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodeN = Base64.getUrlDecoder().decode(modulus);
        byte[] decodeE = Base64.getUrlDecoder().decode(exponent);
        BigInteger n = new BigInteger(1, decodeN);
        BigInteger e = new BigInteger(1, decodeE);

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
        return keyFactory.generatePublic(keySpec);
    }

    public String getKidFromUnsignedTokenHeader(String token, String iss, String aud) {
        return (String) getUnsignedTokenClaims(token, iss, aud).getHeader().get(KID);
    }

    private Jwt<Header, Claims> getUnsignedTokenClaims(String token, String iss, String aud) {
        try {
            return Jwts.parserBuilder()
                .requireAudience(aud)
                .requireIssuer(iss)
                .build()
                .parseClaimsJwt(getUnsignedToken(token));
        } catch (InvalidTokenException e) {
            throw new InvalidTokenException(AuthError.INVALID_TOKEN);
        }
    }

    private String getUnsignedToken(String token) {
        String[] splitToken = token.split("\\.");
        if (splitToken.length != 3) throw new InvalidTokenException(AuthError.INVALID_TOKEN);
        return splitToken[0] + "." + splitToken[1] + ".";
    }
}
