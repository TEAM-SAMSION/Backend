package com.pawith.usermodule.domain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.usermodule.domain.entity.UserAuthority;
import com.pawith.usermodule.domain.repository.UserAuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@DomainService
@RequiredArgsConstructor
public class UserAuthoritySaveService {
    private final UserAuthorityRepository userAuthorityRepository;

    public void saveUserAuthority(final String email) {
        userAuthorityRepository.save(new UserAuthority(email));
    }
}
