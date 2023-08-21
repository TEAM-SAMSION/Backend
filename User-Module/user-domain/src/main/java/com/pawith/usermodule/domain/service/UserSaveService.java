package com.pawith.usermodule.domain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.usermodule.domain.domain.User;
import com.pawith.usermodule.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
public class UserSaveService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(User user) {
            userRepository.save(user);
    }

}
