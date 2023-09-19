package com.pawith.userdomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.enums.Provider;
import com.pawith.commonmodule.exception.Error;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.exception.AccountAlreadyExistException;
import com.pawith.userdomain.exception.UserNotFoundException;
import com.pawith.userdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;

    public void checkAccountAlreadyExist(String email, Provider provider){
        User user = findByEmail(email);
        if(!user.getProvider().equals(provider))
            throw new AccountAlreadyExistException(Error.ACCOUNT_ALREADY_EXIST);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(Error.USER_NOT_FOUND));
    }

    public boolean checkEmailAlreadyExist(String email) {
        return userRepository.existsByEmail(email);
    }
}
