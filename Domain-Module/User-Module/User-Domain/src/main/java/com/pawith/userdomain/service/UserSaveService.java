package com.pawith.userdomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.repository.UserRepository;
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
