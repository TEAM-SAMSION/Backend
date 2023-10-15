package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.dto.TokenReissueResponse;
import com.pawith.authapplication.service.ReissueUseCase;
import com.pawith.authapplication.utils.TokenExtractUtils;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.authdomain.service.TokenDeleteService;
import com.pawith.commonmodule.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@ApplicationService
public class ReissueUseCaseImpl implements ReissueUseCase {
    private final JWTProvider jwtProvider;
    private final TokenDeleteService tokenDeleteService;
    @Override
    public TokenReissueResponse reissue(String refreshTokenHeader) {
        final String refreshToken = TokenExtractUtils.extractToken(refreshTokenHeader);
        final String reissueAccessToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX+jwtProvider.reIssueAccessToken(refreshToken);
        final String reissueRefreshToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX+jwtProvider.reIssueRefreshToken(refreshToken);
        tokenDeleteService.deleteTokenByValue(refreshToken);
        return new TokenReissueResponse(reissueAccessToken, reissueRefreshToken);
    }
}
