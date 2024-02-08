package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.dto.TokenReissueResponse;
import com.pawith.authapplication.service.ReissueUseCase;
import com.pawith.authapplication.utils.TokenExtractUtils;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.authdomain.jwt.PrivateClaims;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.authdomain.service.TokenDeleteService;
import com.pawith.authdomain.service.TokenLockService;
import com.pawith.authdomain.service.TokenSaveService;
import com.pawith.authdomain.service.TokenValidateService;
import com.pawith.commonmodule.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Transactional
@RequiredArgsConstructor
@ApplicationService
public class ReissueUseCaseImpl implements ReissueUseCase {
    private final JWTProvider jwtProvider;
    private final TokenDeleteService tokenDeleteService;
    private final TokenSaveService tokenSaveService;
    private final TokenValidateService tokenValidateService;
    private final TokenLockService tokenLockService;

    @Override
    public TokenReissueResponse reissue(String refreshTokenHeader) {
        final String refreshToken = TokenExtractUtils.extractToken(refreshTokenHeader);
        jwtProvider.validateToken(refreshToken, TokenType.REFRESH_TOKEN);
        final PrivateClaims.UserClaims userClaims = jwtProvider.extractUserClaimsFromToken(refreshToken, TokenType.REFRESH_TOKEN);
        return reissueToken(refreshToken, userClaims);
    }

    private TokenReissueResponse reissueToken(final String refreshToken,final PrivateClaims.UserClaims userClaims) {
        final String lockKey = userClaims.toString();
        try {
            tokenLockService.lockToken(lockKey);
            if (jwtProvider.existsCachedRefreshToken(refreshToken)) {
                return generateToken(jwtProvider::getCachedToken, refreshToken);
            }
            tokenValidateService.validateIsExistToken(refreshToken, TokenType.REFRESH_TOKEN);
            tokenDeleteService.deleteTokenByValue(refreshToken);
            return generateAndSaveToken(jwtProvider::reIssueToken, refreshToken, userClaims.getUserId());
        } finally {
            tokenLockService.releaseLockToken(lockKey);
        }
    }

    private TokenReissueResponse generateToken(final Function<String, JWTProvider.Token> tokenGenerator, final String refreshToken) {
        final JWTProvider.Token token = tokenGenerator.apply(refreshToken);
        final String generatedAccessToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.accessToken();
        final String generatedRefreshToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.refreshToken();
        return new TokenReissueResponse(generatedAccessToken, generatedRefreshToken);
    }
    private TokenReissueResponse generateAndSaveToken(final Function<String, JWTProvider.Token> tokenGenerator,final String refreshToken,final Long userId) {
        final JWTProvider.Token token = tokenGenerator.apply(refreshToken);
        tokenSaveService.saveToken(token.refreshToken(), TokenType.REFRESH_TOKEN, userId);
        return generateToken(inputRefreshToken -> token, refreshToken);
    }

}
