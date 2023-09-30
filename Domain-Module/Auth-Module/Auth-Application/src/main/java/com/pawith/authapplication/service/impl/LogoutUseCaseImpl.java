package com.pawith.authapplication.service.impl;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.service.LogoutUseCase;
import com.pawith.authapplication.utils.TokenExtractUtils;
import com.pawith.authdomain.entity.Token;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.authdomain.service.TokenDeleteService;
import com.pawith.authdomain.service.TokenQueryService;
import com.pawith.commonmodule.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@ApplicationService
@RequiredArgsConstructor
@Transactional
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final TokenDeleteService tokenDeleteService;
    private final TokenQueryService tokenQueryService;

    @Override
    public void logoutAccessUser() {
        final String refreshTokenHeader = getHeader(AuthConsts.REFRESH_TOKEN_HEADER);
        final String refreshToken = TokenExtractUtils.extractToken(refreshTokenHeader);
        final Token refreshTokenEntity = tokenQueryService.findTokenByValue(refreshToken, TokenType.REFRESH_TOKEN);
        tokenDeleteService.deleteRefreshToken(refreshTokenEntity);
    }

    private String getHeader(final String name){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest().getHeader(name);
    }
}
