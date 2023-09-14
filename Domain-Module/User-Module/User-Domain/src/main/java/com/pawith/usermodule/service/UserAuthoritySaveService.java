package com.pawith.usermodule.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.usermodule.entity.UserAuthority;
import com.pawith.usermodule.repository.UserAuthorityRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class UserAuthoritySaveService {
    private final UserAuthorityRepository userAuthorityRepository;

    public void saveUserAuthority(final String email) {
        userAuthorityRepository.save(new UserAuthority(email));
    }
}
