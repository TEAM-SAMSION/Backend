package com.pawith.usermodule.domain.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.usermodule.entity.UserAuthority;
import com.pawith.usermodule.exception.UserAuthorityNotFoundException;
import com.pawith.usermodule.repository.UserAuthorityRepository;
import com.pawith.usermodule.service.UserAuthorityQueryService;
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

    private static FixtureMonkey getFixtureMonkey() {
        return FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .build();
    }

    @BeforeEach
    void init() {
        userAuthorityQueryService = new UserAuthorityQueryService(userAuthorityRepository);
    }

    @Test
    @DisplayName("유저 권한을 조회한다.")
    void findByEmail() {
        //given
        final String email = FixtureMonkey.create().giveMeOne(String.class);
        final UserAuthority userAuthority = getFixtureMonkey()
            .giveMeBuilder(UserAuthority.class)
            .set("email", email)
            .sample();
        given(userAuthorityRepository.findByEmail(email)).willReturn(Optional.of(userAuthority));
        //when
        UserAuthority result = userAuthorityQueryService.findByEmail(email);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(userAuthority);
    }

    @Test
    @DisplayName("유저 권한을 조회할 수 없으면 예외가 발생한다.")
    void findByEmailThrowException() {
        //given
        final String email = FixtureMonkey.create().giveMeOne(String.class);
        given(userAuthorityRepository.findByEmail(email)).willReturn(Optional.empty());
        //when
        //then
        Assertions.assertThatCode(() -> userAuthorityQueryService.findByEmail(email))
            .isInstanceOf(UserAuthorityNotFoundException.class);
    }

}