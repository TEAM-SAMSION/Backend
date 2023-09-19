package com.pawith.userdomain.service;


import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.repository.UserRepository;
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

    @BeforeEach
    void init() { userSaveService = new UserSaveService(userRepository); }

    @Test
    @DisplayName("유저를 생성한다.")
    void saveUser() {
        //given
        User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(User.class);
        //when
        userSaveService.saveUser(mockUser);
        //then
        then(userRepository).should(times(1)).save(mockUser);
    }
}
