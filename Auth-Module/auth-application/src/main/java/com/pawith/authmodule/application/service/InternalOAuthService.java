package com.pawith.authmodule.application.service;

import com.pawith.authmodule.application.common.exception.AccountAlreadyExistException;
import com.pawith.authmodule.application.dto.OAuthUserInfo;
import com.pawith.authmodule.application.dto.Provider;
import com.pawith.commonmodule.exception.Error;
import com.pawith.usermodule.application.handler.event.UserSignUpEvent;
import com.pawith.usermodule.domain.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InternalOAuthService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void auth(OAuthUserInfo oAuthUserInfo, Provider provider) {
        // Util 클래스로 통합할 예정
        Optional<User> findUser = userRepository.findByEmail(oAuthUserInfo.getEmail());
        if(isNewMember(findUser))
            joinMembership(oAuthUserInfo, provider);
        else
            login(findUser, provider);
    }

    private void joinMembership(OAuthUserInfo oAuthUserInfo, Provider provider) {
        publisher.publishEvent(new UserSignUpEvent(oAuthUserInfo.getUsername(), oAuthUserInfo.getEmail(), provider.toString()));
    }

    private static boolean isNewMember(Optional<User> findUser) {
        return findUser.isEmpty();
    }

    private void login(Optional<User> user, Provider provider) {
        User loginUser = user.get();
        if(loginUser.getProvider().equals(provider.toString()))
            throw new AccountAlreadyExistException(Error.ACCOUNT_ALREADY_EXIST);
    }
}
