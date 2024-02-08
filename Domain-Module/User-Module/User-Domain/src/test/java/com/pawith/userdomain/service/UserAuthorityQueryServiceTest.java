package com.pawith.userdomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.entity.UserAuthority;
import com.pawith.userdomain.exception.UserAuthorityNotFoundException;
import com.pawith.userdomain.repository.UserAuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@Slf4j
@UnitTestConfig
@DisplayName("UserAuthorityQueryService 테스트")
class UserAuthorityQueryServiceTest {

    @Mock
    UserAuthorityRepository userAuthorityRepository;

    UserAuthorityQueryService userAuthorityQueryService;

    @BeforeEach
    void init() {
        userAuthorityQueryService = new UserAuthorityQueryService(userAuthorityRepository);
    }

    @Test
    @DisplayName("유저 권한을 조회한다.")
    void findByEmail() {
        //given
        final User user = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(User.class);
        final UserAuthority userAuthority = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeBuilder(UserAuthority.class)
            .set("user", user)
            .sample();
        given(userAuthorityRepository.findByUserId(user.getId())).willReturn(Optional.of(userAuthority));
        //when
        UserAuthority result = userAuthorityQueryService.findByUserId(user.getId());
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(userAuthority);
    }

    @Test
    @DisplayName("유저 권한을 조회할 수 없으면 예외가 발생한다.")
    void findByEmailThrowException() {
        //given
        final Long userId = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Long.class);
        given(userAuthorityRepository.findByUserId(userId)).willReturn(Optional.empty());
        //when
        //then
        Assertions.assertThatCode(() -> userAuthorityQueryService.findByUserId(userId))
            .isInstanceOf(UserAuthorityNotFoundException.class);
    }

}