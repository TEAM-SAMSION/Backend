package com.pawith.userdomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.entity.UserAuthority;
import com.pawith.userdomain.repository.UserAuthorityRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class UserAuthoritySaveService {
    private final UserAuthorityRepository userAuthorityRepository;

    public void saveUserAuthority(final User user) {
        userAuthorityRepository.findByUserId(user.getId())
            .ifPresentOrElse(
                UserAuthority::initialUserAuthority,
                () -> userAuthorityRepository.save(UserAuthority.of(user)));
    }
}
