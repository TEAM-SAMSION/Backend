package com.pawith.authdomain.service;

import com.pawith.authdomain.entity.OAuth;
import com.pawith.authdomain.exception.AuthError;
import com.pawith.authdomain.exception.OAuthNotFoundException;
import com.pawith.authdomain.repository.OAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthQueryService {
    private final OAuthRepository oAuthRepository;

    public boolean existBySub(final String sub) {
        return oAuthRepository.existsBySub(sub);
    }

    public OAuth findBySub(final String sub) {
        return oAuthRepository.findBySub(sub)
            .orElseThrow(() -> new OAuthNotFoundException(AuthError.OAUTH_NOT_FOUND));
    }

    public boolean existByUserId(final Long userId) {
        return oAuthRepository.existsByUserId(userId);
    }
}
