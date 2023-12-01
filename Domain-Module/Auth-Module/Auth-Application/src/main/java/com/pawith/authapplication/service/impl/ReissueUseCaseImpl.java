package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.dto.TokenReissueResponse;
import com.pawith.authapplication.service.ReissueUseCase;
import com.pawith.authapplication.utils.TokenExtractUtils;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.authdomain.service.TokenDeleteService;
import com.pawith.authdomain.service.TokenLockService;
import com.pawith.authdomain.service.TokenSaveService;
import com.pawith.authdomain.service.TokenValidateService;
import com.pawith.commonmodule.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
        final String userEmail = jwtProvider.extractEmailFromToken(refreshToken, TokenType.REFRESH_TOKEN);
        return reissueToken(refreshToken, userEmail);
    }

    private TokenReissueResponse reissueToken(final String refreshToken,final String userEmail) {
        try {
            tokenLockService.lockToken(userEmail);
            if (jwtProvider.existsCachedRefreshToken(refreshToken)) {
                final JWTProvider.Token cachedToken = jwtProvider.getCachedToken(refreshToken);
                final String cachedAccessToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + cachedToken.accessToken();
                final String cachedRefreshToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + cachedToken.refreshToken();
                return new TokenReissueResponse(cachedAccessToken, cachedRefreshToken);
            }

            tokenValidateService.validateIsExistToken(refreshToken, TokenType.REFRESH_TOKEN);
            tokenDeleteService.deleteTokenByValue(refreshToken);
            final JWTProvider.Token reIssueToken = jwtProvider.reIssueToken(refreshToken);
            final String reissueAccessToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + reIssueToken.accessToken();
            final String reissueRefreshToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + reIssueToken.refreshToken();

            tokenSaveService.saveToken(reIssueToken.refreshToken(), userEmail, TokenType.REFRESH_TOKEN);

            return new TokenReissueResponse(reissueAccessToken, reissueRefreshToken);
        } finally {
            tokenLockService.releaseLockToken(userEmail);
        }
    }


}
