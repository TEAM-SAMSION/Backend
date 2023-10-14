package com.pawith.authapplication.service.impl;

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

@Slf4j
@ApplicationService
@RequiredArgsConstructor
@Transactional
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final TokenDeleteService tokenDeleteService;
    private final TokenQueryService tokenQueryService;

    @Override
    public void logoutAccessUser(String refreshTokenHeader) {
        final String refreshToken = TokenExtractUtils.extractToken(refreshTokenHeader);
        final Token refreshTokenEntity = tokenQueryService.findTokenByValue(refreshToken, TokenType.REFRESH_TOKEN);
        tokenDeleteService.deleteToken(refreshTokenEntity);
    }
}
