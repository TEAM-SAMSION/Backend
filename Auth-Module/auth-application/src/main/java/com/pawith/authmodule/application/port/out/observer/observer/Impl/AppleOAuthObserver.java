package com.pawith.authmodule.application.port.out.observer.observer.Impl;

import com.pawith.authmodule.application.dto.OAuthUserInfo;
import com.pawith.authmodule.application.dto.Provider;
import com.pawith.authmodule.application.port.out.observer.observer.AbstractOAuthObserver;
import com.pawith.authmodule.application.port.out.observer.observer.feign.AppleFeignClient;
import com.pawith.authmodule.application.port.out.observer.observer.feign.response.Keys;
import com.pawith.authmodule.application.port.out.observer.observer.feign.response.Keys.PubKey;
import com.pawith.commonmodule.exception.Error;
import com.pawith.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
public class AppleOAuthObserver extends AbstractOAuthObserver {
    private static final Provider PROVIDER = Provider.APPLE;
    private final RestTemplate restTemplate;
    private final AppleFeignClient appleFeignClient;
    @Value("${app-id.apple}")
    private String apple_aud;

    @Override
    protected OAuthUserInfo attemptLogin(String accessToken) {
        log.info("AppleOAuthObserver attemptLogin");
        // 토큰 서명 검증
        Jws<Claims> oidcTokenJws = sigVerificationAndGetJws(accessToken);
        // 토큰 바디 파싱해서 사용자 정보 획득
        String email = (String) oidcTokenJws.getBody().get("email");
        return new OAuthUserInfo("포잇", email, PROVIDER.toString());
    }

    @Override
    public boolean isLogin(Provider provider) {
        log.info("AppleOAuthObserver isLogin");
        return PROVIDER.equals(provider);
    }

    private Jws<Claims> sigVerificationAndGetJws(String unverifiedToken) {

        String kid = getKidFromUnsignedTokenHeader(
                unverifiedToken,
                "https://appleid.apple.com",
                apple_aud);

        Keys keys = appleFeignClient.getKeys();
        PubKey pubKey = keys.getKeys().stream()
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
        } catch (Exception e) {
            log.error(e.toString());
            throw new InvalidTokenException(Error.INVALID_TOKEN);
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
        } catch (Exception e) {
            log.error(e.toString());
            throw new InvalidTokenException(Error.INVALID_TOKEN);
        }
    }

    private String getUnsignedToken(String token) {
        String[] splitToken = token.split("\\.");
        if (splitToken.length != 3) throw new InvalidTokenException(Error.INVALID_TOKEN);
        return splitToken[0] + "." + splitToken[1] + ".";
    }
}

