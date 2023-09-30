package com.pawith.authdomain.service.impl;

import com.pawith.authdomain.entity.Token;
import com.pawith.authdomain.repository.TokenRepository;
import com.pawith.authdomain.service.TokenDeleteService;
import com.pawith.commonmodule.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class TokenDeleteServiceImpl implements TokenDeleteService {

    private final TokenRepository tokenRepository;

    public void deleteRefreshToken(final Token token){
        tokenRepository.delete(token);
    }
}
