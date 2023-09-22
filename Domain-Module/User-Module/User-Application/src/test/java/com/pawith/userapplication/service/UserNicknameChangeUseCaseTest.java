package com.pawith.userapplication.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.userapplication.dto.request.UserNicknameChangeRequest;
import com.pawith.userdomain.entity.Authority;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.entity.UserAuthority;
import com.pawith.userdomain.service.UserAuthorityQueryService;
import com.pawith.userdomain.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;

@Slf4j
@UnitTestConfig
@DisplayName("UserNicknameChangeUseCase 테스트")
class UserNicknameChangeUseCaseTest {

    @Mock
    private UserUtils userUtils;
    @Mock
    private UserAuthorityQueryService userAuthorityQueryService;

    private UserNicknameChangeUseCase userNicknameChangeUseCase;

    @BeforeEach
    void init() {
        userNicknameChangeUseCase = new UserNicknameChangeUseCase(userUtils, userAuthorityQueryService);
    }

    @Test
    @DisplayName("유저 닉네임을 변경한다.")
    void changeUserName() {
        //given
        final UserNicknameChangeRequest mockRequest = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeOne(UserNicknameChangeRequest.class);
        final User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeOne(User.class);
        final UserAuthority mockUserAuthority = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeBuilder(UserAuthority.class)
            .set("authority", Authority.GUEST)
            .sample();
        given(userUtils.getAccessUser()).willReturn(mockUser);
        given(userAuthorityQueryService.findByEmail(mockUser.getEmail())).willReturn(mockUserAuthority);
        //when
        userNicknameChangeUseCase.changeUserName(mockRequest);
        //then
        Assertions.assertThat(mockUser.getNickname()).isEqualTo(mockRequest.getNickname());
        Assertions.assertThat(mockUserAuthority.getAuthority()).isEqualTo(Authority.USER);
    }
}