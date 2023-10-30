package com.pawith.userdomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.userdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class UserDeleteService {
    private final UserRepository userRepository;

    public void deleteUser(final Long userId){
        userRepository.deleteById(userId);
    }
}
