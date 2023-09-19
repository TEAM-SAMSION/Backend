package com.pawith.userapplication.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.security.WithMockAuthUser;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.userapplication.dto.response.UserInfoResponse;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("UserInfoGetUseCase 테스트")
class UserInfoGetUseCaseTest {

    @Mock
    private UserUtils userUtils;

    private UserInfoGetUseCase userInfoGetUseCase;

    @BeforeEach
    void init(){
        userInfoGetUseCase = new UserInfoGetUseCase(userUtils);
    }

    @Test
    @WithMockAuthUser
    @DisplayName("유저 정보를 조회한다.")
    void getUserInfo() {
        //given
        User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
        .giveMeBuilder(User.class).sample();
        given(userUtils.getAccessUser()).willReturn(mockUser);
        //when
        UserInfoResponse userInfo = userInfoGetUseCase.getUserInfo();
        //then
        Assertions.assertThat(userInfo).usingRecursiveComparison().isEqualTo(new UserInfoResponse(mockUser.getNickname(), mockUser.getEmail(), mockUser.getImageUrl()));
    }
}