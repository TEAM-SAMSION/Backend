package com.pawith.usermodule.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.repository.UserRepository;
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
