package com.pawith.userdomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.userdomain.entity.UserAuthority;
import com.pawith.userdomain.exception.UserAuthorityNotFoundException;
import com.pawith.userdomain.exception.UserError;
import com.pawith.userdomain.repository.UserAuthorityRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class UserAuthorityQueryService {
    private final UserAuthorityRepository userAuthorityRepository;

    public UserAuthority findByEmail(final String email) {
        final UserAuthority userAuthority = userAuthorityRepository.findByEmail(email)
            .orElseThrow(() -> new UserAuthorityNotFoundException(UserError.USER_AUTHORITY_NOT_FOUND));
        return userAuthority;
    }
}
