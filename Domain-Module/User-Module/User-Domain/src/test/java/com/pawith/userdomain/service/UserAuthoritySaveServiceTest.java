package com.pawith.userdomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.userdomain.entity.UserAuthority;
import com.pawith.userdomain.repository.UserAuthorityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@UnitTestConfig
@DisplayName("UserAuthoritySaveService 테스트")
class UserAuthoritySaveServiceTest {

    @Mock
    UserAuthorityRepository userAuthorityRepository;

    UserAuthoritySaveService userAuthoritySaveService;

    @BeforeEach
    void init() {
        userAuthoritySaveService = new UserAuthoritySaveService(userAuthorityRepository);
    }

    @Test
    @DisplayName("유저 권한을 생성한다.")
    void saveUserAuthority() {
        //given
        final String email = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        given(userAuthorityRepository.findByEmail(email)).willReturn(Optional.empty());
        //when
        userAuthoritySaveService.saveUserAuthority(email);
        //then
        then(userAuthorityRepository).should().save(any());
    }

    @Test
    @DisplayName("유저 권한 존재하면 생성하지 않는다.")
    void saveUserAuthorityWhenUserAuthorityExist() {
        //given
        final String email = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        given(userAuthorityRepository.findByEmail(email)).willReturn(Optional.of(new UserAuthority(email)));
        //when
        userAuthoritySaveService.saveUserAuthority(email);
        //then
        then(userAuthorityRepository).should().findByEmail(email);
        then(userAuthorityRepository).shouldHaveNoMoreInteractions();
    }

}