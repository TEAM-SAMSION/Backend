package com.pawith.usermodule.domain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.usermodule.domain.entity.UserAuthority;
import com.pawith.usermodule.domain.exception.UserAuthorityNotFoundException;
import com.pawith.usermodule.domain.repository.UserAuthorityRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class UserAuthorityQueryService {
    private final UserAuthorityRepository userAuthorityRepository;

    public UserAuthority findByEmail(final String email) {
        final UserAuthority userAuthority = userAuthorityRepository.findByEmail(email)
            .orElseThrow(() -> new UserAuthorityNotFoundException(Error.USER_AUTHORITY_NOT_FOUND));
        return userAuthority;
    }
}
