package com.pawith.user.application.handler;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.commonmodule.exception.Error;
import com.pawith.usermodule.application.handler.UserSignUpHandler;
import com.pawith.usermodule.application.handler.event.UserSignUpEvent;
import com.pawith.usermodule.domain.entity.User;
import com.pawith.usermodule.domain.exception.AccountAlreadyExistException;
import com.pawith.usermodule.domain.service.UserQueryService;
import com.pawith.usermodule.domain.service.UserSaveService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("UserSignUpHandler 테스트")
public class UserSignUpHandlerTest {

    @Mock
    UserSaveService userSaveService;
    @Mock
    UserQueryService userQueryService;
    UserSignUpHandler userSignUpHandler;

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .build();

    public static final String PROVIDER = "provider";
    public static final String DIFFERENT_PROVIDER = "different_provider";

    @BeforeEach
    void init() { userSignUpHandler = new UserSignUpHandler(userSaveService, userQueryService); }

    @Test
    @DisplayName("첫 로그인 시, 사용자 가입 요청을 처리한다.")
    void userSignUp() {
        //given
        final UserSignUpEvent mockUserSignUpEvent = getMockUserSignUpEvent();
        System.out.println(mockUserSignUpEvent.getProvider());
        //when
        userSignUpHandler.signUp(mockUserSignUpEvent);
        //then
        then(userSaveService).should(times(1)).saveUser(any());
    }

    @Test
    @DisplayName("이미 계정이 존재할때, 다른 소셜 로그인을 하는 경우 예외가 발생한다")
    void userSignUpWithDifferentProvider() {
        //given
        final UserSignUpEvent mockUserSignUpEvent = getMockUserSignUpEvent();
        given(userQueryService.checkEmailAlreadyExist(mockUserSignUpEvent.getEmail())).willReturn(true);
        doThrow(new AccountAlreadyExistException(Error.ACCOUNT_ALREADY_EXIST))
                .when(userQueryService)
                .checkAccountAlreadyExist((mockUserSignUpEvent.getEmail()), (mockUserSignUpEvent.getProvider()));
        //when
        //then
        Assertions.assertThatCode(() -> userSignUpHandler.signUp(mockUserSignUpEvent))
                .isInstanceOf(AccountAlreadyExistException.class);
    }

    UserSignUpEvent getMockUserSignUpEvent() {
        return fixtureMonkey.giveMeBuilder(UserSignUpEvent.class)
                .set("provider", PROVIDER)
                .sample();
    }

}
