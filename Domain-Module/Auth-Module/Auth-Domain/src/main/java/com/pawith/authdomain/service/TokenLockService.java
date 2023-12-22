package com.pawith.authdomain.service;

import com.pawith.authdomain.repository.TokenLockRepository;
import com.pawith.commonmodule.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TokenLockService {

    private final TokenLockRepository tokenLockRepository;

    public void lockToken(final String token){
        tokenLockRepository.getNamedLock(token);
    }

    public void releaseLockToken(final String token){
        tokenLockRepository.releaseNamedLock(token);
    }
}
