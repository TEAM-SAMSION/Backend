package com.pawith.usermodule.domain.service;


import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.repository.UserRepository;
import com.pawith.usermodule.service.UserSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@UnitTestConfig
@DisplayName("UserSaveService 테스트")
public class UserSaveServiceTest {

    @Mock
    UserRepository userRepository;
    UserSaveService userSaveService;

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .build();

    @BeforeEach
    void init() { userSaveService = new UserSaveService(userRepository); }

    @Test
    @DisplayName("유저를 생성한다.")
    void saveUser() {
        //given
        User mockUser = fixtureMonkey.giveMeOne(User.class);
        //when
        userSaveService.saveUser(mockUser);
        //then
        then(userRepository).should(times(1)).save(mockUser);
    }
}
