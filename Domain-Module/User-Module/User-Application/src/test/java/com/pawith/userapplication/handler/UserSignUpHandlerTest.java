package com.pawith.userapplication.handler;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.event.UserSignUpEvent;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.userdomain.exception.AccountAlreadyExistException;
import com.pawith.userdomain.service.UserAuthoritySaveService;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.service.UserSaveService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@UnitTestConfig
@DisplayName("UserSignUpHandler 테스트")
public class UserSignUpHandlerTest {

    @Mock
    UserSaveService userSaveService;
    @Mock
    UserQueryService userQueryService;
    @Mock
    UserAuthoritySaveService userAuthoritySaveService;

    UserSignUpHandler userSignUpHandler;

    @BeforeEach
    void init() {
        userSignUpHandler = new UserSignUpHandler(userSaveService, userQueryService, userAuthoritySaveService);
    }

    @Test
    @DisplayName("첫 로그인 시, 사용자 가입 요청을 처리한다.")
    void userSignUp() {
        //given
        final UserSignUpEvent mockUserSignUpEvent = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(UserSignUpEvent.class);
        //when
        userSignUpHandler.signUp(mockUserSignUpEvent);
        //then
        then(userSaveService).should(times(1)).saveUser(any());
        then(userAuthoritySaveService).should(times(1)).saveUserAuthority(any());
    }

}
