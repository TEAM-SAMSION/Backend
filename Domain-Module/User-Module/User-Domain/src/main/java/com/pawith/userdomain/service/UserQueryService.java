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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        return findUser(userRepository::findByEmail, email);
    }

    public User findById(Long userId){
        return findUser(userRepository::findById, userId);
    }

    public <T> Map<Long,User> findUserMapByIds(List<Long> userIds){
        return userRepository.findAllByIds(userIds)
            .stream()
            .collect(Collectors.toMap(User::getId, Function.identity()));
    }

    private <T> User findUser(Function<T, Optional<User>> method, T specificationData){
        return method.apply(specificationData)
                .orElseThrow(() -> new UserNotFoundException(Error.USER_NOT_FOUND));
    }

    public boolean checkEmailAlreadyExist(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> findAllByNicknameAndIds(String nickname, List<Long> userIds) {
        return userRepository.findAllByNicknameAndIds(nickname, userIds);
    }
}
