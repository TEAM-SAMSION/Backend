package com.pawith.usermodule.domain.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.usermodule.domain.repository.UserAuthorityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
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
        final String email = FixtureMonkey.create().giveMeOne(String.class);
        //when
        userAuthoritySaveService.saveUserAuthority(email);
        //then
        then(userAuthorityRepository).should().save(any());
    }

}