package com.pawith.todoapplication.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.request.AuthorityChangeRequest;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.exception.UnchangeableException;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.RegisterValidateService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("ChangeRegisterUseCase 테스트")
public class ChangeRegisterUseCaseTest {

    @Mock
    private RegisterQueryService registerQueryService;
    @Mock
    private RegisterValidateService registerValidateService;
    @Mock
    private UserUtils userUtils;
    private ChangeRegisterUseCase changeRegisterUseCase;

    @BeforeEach
    void init() {
        changeRegisterUseCase = new ChangeRegisterUseCase(registerQueryService, registerValidateService, userUtils);
    }

    @Test
    @DisplayName("Register 권한 변경 테스트 - 관리자가 회원을 관리자로 변경")
    void changeAuthorityToPresident() {
        // given
        final User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(User.class);
        final Register mockUserRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("Authority", "PRESIDENT")
            .sample();
        final Register mockRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("Authority", "MEMBER")
            .sample();

        final Long registerId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final AuthorityChangeRequest authorityChangeRequest = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(AuthorityChangeRequest.class)
            .set("authority", "PRESIDENT")
            .sample();

        given(userUtils.getAccessUser()).willReturn(mockUser);
        given(registerQueryService.findRegisterByTodoTeamIdAndUserId(todoTeamId, mockUser.getId())).willReturn(mockUserRegister);
        given(registerQueryService.findRegisterById(registerId)).willReturn(mockRegister);
        // when
        changeRegisterUseCase.changeAuthority(todoTeamId, registerId, authorityChangeRequest);
        // then
        Assertions.assertThat(mockRegister.getAuthority()).isEqualTo(Authority.valueOf(authorityChangeRequest.getAuthority()));
        Assertions.assertThat(mockUserRegister.getAuthority()).isEqualTo(Authority.MEMBER);
    }

    @Test
    @DisplayName("Register 권한 변경 테스트")
    void changeAuthority() {
        // given
        final User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(User.class);
        final Register mockUserRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("Authority", "PRESIDENT")
            .sample();
        final Register mockRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("Authority", "MEMBER")
            .sample();

        final Long registerId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final AuthorityChangeRequest authorityChangeRequest = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(AuthorityChangeRequest.class)
            .set("authority", "EXECUTIVE")
            .sample();

        given(userUtils.getAccessUser()).willReturn(mockUser);
        given(registerQueryService.findRegisterByTodoTeamIdAndUserId(todoTeamId, mockUser.getId())).willReturn(mockUserRegister);
        given(registerQueryService.findRegisterById(registerId)).willReturn(mockRegister);
        // when
        changeRegisterUseCase.changeAuthority(todoTeamId, registerId, authorityChangeRequest);
        // then
        Assertions.assertThat(mockRegister.getAuthority()).isEqualTo(Authority.valueOf(authorityChangeRequest.getAuthority()));
    }
}
