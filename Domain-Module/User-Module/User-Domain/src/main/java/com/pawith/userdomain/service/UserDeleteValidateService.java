package com.pawith.userdomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.exception.UserDeleteException;
import com.pawith.userdomain.exception.UserError;
import com.pawith.userdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class UserDeleteValidateService {
    private final UserRepository userRepository;
    public void validateDeleteUser(final User user) {
        final boolean isNotDeletable = userRepository.existsRegisterByUserId(user.getId());
        if (isNotDeletable) {
            throw new UserDeleteException(UserError.CANNOT_DELETE_USER);
        }
    }
}
