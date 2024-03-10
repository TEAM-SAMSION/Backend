package com.pawith.authdomain.service.impl;

import com.pawith.authdomain.entity.Token;
import com.pawith.authdomain.repository.TokenRepository;
import com.pawith.authdomain.service.TokenDeleteService;
import com.pawith.commonmodule.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class TokenDeleteServiceImpl implements TokenDeleteService {

    private final TokenRepository tokenRepository;

    public void deleteToken(final Token token){
        tokenRepository.delete(token);
    }

    public void deleteAllToken(final List<Token> tokenList){
        tokenRepository.deleteAll(tokenList);
    }

    @Override
    public void deleteTokenByValue(String value) {
        tokenRepository.deleteByValue(value);
    }
}
