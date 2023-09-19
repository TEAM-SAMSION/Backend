package com.pawith.userdomain.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.enums.Provider;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.exception.AccountAlreadyExistException;
import com.pawith.userdomain.exception.UserNotFoundException;
import com.pawith.userdomain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Slf4j
@UnitTestConfig
@DisplayName("UserQueryService 테스트")
public class UserQueryServiceTest {

    @Mock
    UserRepository userRepository;

    UserQueryService userQueryService;

    @BeforeEach
    void init() {
        userQueryService = new UserQueryService(userRepository);
    }

    @Test
    @DisplayName("email을 통해 사용자정보를 가져온다.")
    void findByEmail() {
        //given
        User mockUser = getFixtureMonkey().giveMeBuilder(User.class)
            .sample();
        given(userRepository.findByEmail(any())).willReturn(Optional.of(mockUser));
        //when
        User user = userQueryService.findByEmail(mockUser.getEmail());
        //then
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user).usingRecursiveComparison().isEqualTo(mockUser);
    }

    @Test
    @DisplayName("email을 통해 사용자정보를 가져올 때, 사용자가 존재하지 않으면 UserNotFoundException을 발생시킨다.")
    void findByEmailThrowUserNotFoundException() {
        //given
        User mockUser = getFixtureMonkey().giveMeBuilder(User.class)
            .sample();
        given(userRepository.findByEmail(any())).willReturn(Optional.empty());
        //when
        //then
        Assertions.assertThatThrownBy(() -> userQueryService.findByEmail(mockUser.getEmail()))
            .isInstanceOf(UserNotFoundException.class);
    }


    @Test
    @DisplayName("사용자 계정이 존재하면 AccountAlreadyExistException을 발생시킨다.")
    void checkAccountAlreadyExist() {
        //given
        final User mockUser = getFixtureMonkey().giveMeBuilder(User.class)
            .set("id",1L)
            .set("provider", Provider.NAVER)
            .sample();
        given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));
        log.info("mockUser: {}", mockUser);
        //when
        //then
        Assertions.assertThatThrownBy(() -> userQueryService.checkAccountAlreadyExist(mockUser.getEmail(), Provider.GOOGLE))
            .isInstanceOf(AccountAlreadyExistException.class);
    }

    @Test
    @DisplayName("사용자 계정이 존재하지 않으면 예외가 발생하지 않는다.")
    void checkAccountAlreadyExistNotThrow() {
        //given
        final User mockUser = getFixtureMonkey().giveMeBuilder(User.class)
            .set("id",1L)
            .set("provider", Provider.NAVER)
            .sample();
        given(userRepository.findByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser));
        log.info("mockUser: {}", mockUser);
        //when
        //then
        Assertions.assertThatCode(() -> userQueryService.checkAccountAlreadyExist(mockUser.getEmail(), Provider.NAVER))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("email이 존재하면 true를 반환한다.")
    void checkEmailAlreadyExist() {
        //given
        final User mockUser = getFixtureMonkey().giveMeBuilder(User.class)
            .set("id",1L)
            .set("provider", Provider.NAVER)
            .sample();
        given(userRepository.existsByEmail(mockUser.getEmail())).willReturn(true);
        //when
        boolean result = userQueryService.checkEmailAlreadyExist(mockUser.getEmail());
        //then
        Assertions.assertThat(result).isTrue();
    }

    private FixtureMonkey getFixtureMonkey(){
        return FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .build();
    }



}
