package com.pawith.authdomain.service;

import com.pawith.authdomain.entity.OAuth;
import com.pawith.authdomain.repository.OAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthSaveService {
    private final OAuthRepository oAuthRepository;

    public void save(final OAuth oAuth) {
        oAuthRepository.save(oAuth);
    }
}
