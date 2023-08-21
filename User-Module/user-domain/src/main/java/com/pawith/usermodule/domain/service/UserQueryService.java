package com.pawith.usermodule.domain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.usermodule.domain.entity.User;
import com.pawith.usermodule.domain.exception.UserNotFoundException;
import com.pawith.usermodule.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(Error.USER_NOT_FOUND));
    }

}
