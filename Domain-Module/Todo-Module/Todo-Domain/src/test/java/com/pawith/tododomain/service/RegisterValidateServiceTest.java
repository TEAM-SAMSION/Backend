package com.pawith.tododomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.exception.UnchangeableException;
import com.pawith.tododomain.exception.UnregistrableException;
import com.pawith.tododomain.repository.RegisterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.BDDMockito.given;


@UnitTestConfig
@DisplayName("RegisterValidateService 테스트")
class RegisterValidateServiceTest {

    @Mock
    private RegisterRepository registerRepository;

    private RegisterValidateService registerValidateService;

    @BeforeEach
    void init() {
        registerValidateService = new RegisterValidateService(registerRepository);
    }

    @Test
    @DisplayName("운영진 register 탈퇴 가능 여부 테스트 - 운영진이 1명이고, register가 2명 이상일 때 UnregisterableException 발생")
    void validatePresidentRegisterDeletable_one_president_two_register_throw_unregisterableException() {
        // given
        final Register mockRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("isRegistered", true)
            .set("authority", Authority.PRESIDENT)
            .sample();
        final Integer registerCount = 2;
        given(registerRepository.countByTodoTeamIdQuery(mockRegister.getTodoTeam().getId())).willReturn(registerCount);
        // when
        // then
        Assertions.assertThatCode(() -> registerValidateService.validatePresidentRegisterDeletable(mockRegister)).isInstanceOf(UnregistrableException.class);
    }

    @Test
    @DisplayName("register 탈퇴 가능 여부 테스트 - 운영진이 1명 이상이고, register가 1명일 때 UnregisterableException 발생하지 않음")
    void validateRegisterDeletable_one_over_president_one_register() {
        // given
        final Register mockRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("isRegistered", true)
            .set("authority", Authority.MEMBER)
            .sample();
        final Integer registerCount = 1;
        BDDMockito.lenient().when(registerRepository.countByTodoTeamIdQuery(mockRegister.getTodoTeam().getId())).thenReturn(registerCount);
        // when
        // then
        Assertions.assertThatCode(() -> registerValidateService.validatePresidentRegisterDeletable(mockRegister)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사용자 탈퇴 가능 여부 테스트 - 참여중인 todo team 중 운영진으로 포함된 todo team이 1개 이상일 때 UnregisterableException 발생")
    void validateRegisterDeletable_with_president_register() {
        // given
        final List<Register> registerList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("isRegistered", true)
            .set("authority", Authority.PRESIDENT)
            .sampleList(5);
        registerList.forEach(register -> {
            final Integer registerCount = 2;
            BDDMockito.lenient().when(registerRepository.countByTodoTeamIdQuery(register.getTodoTeam().getId())).thenReturn(registerCount);
        });
        // when
        // then
        Assertions.assertThatCode(() -> registerValidateService.validateRegisterDeletable(registerList)).isInstanceOf(UnregistrableException.class);
    }

    @Test
    @DisplayName("사용자 탈퇴 가능 여부 테스트 - 참여중인 todo team 중 운영진으로 포함된 todo team이 1개 이하일 때 UnregisterableException 발생하지 않음")
    void validateRegisterDeletable_without_president_register() {
        // given
        final List<Register> registerList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("isRegistered", true)
            .set("authority", Authority.MEMBER)
            .sampleList(5);
        registerList.forEach(register -> {
            final Integer registerCount = 1;
            BDDMockito.lenient().when(registerRepository.countByTodoTeamIdQuery(register.getTodoTeam().getId())).thenReturn(registerCount);
        });
        // when
        // then
        Assertions.assertThatCode(() -> registerValidateService.validateRegisterDeletable(registerList)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사용자 권한 변경 가능 여부 테스트 - 사용자가 운영진이 아니고 권한 파라미터가 운영진이 아닌경우 UnchangeableException 발생하지 않음")
    void validateAuthorityChangeable_with_member() {
        // given
        final Register mockRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("isRegistered", true)
            .set("authority", Authority.MEMBER)
            .sample();
        // when
        // then
        Assertions.assertThatCode(() -> registerValidateService.validateAuthorityChangeable(mockRegister, Authority.MEMBER)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사용자 권한 변경 가능 여부 테스트 - 사용자가 운영진이고 권한 파라미터가 운영진인 경우 UnchangeableException 발생하지 않음")
    void validateAuthorityChangeable_with_president() {
        // given
        final Register mockRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("isRegistered", true)
            .set("authority", Authority.PRESIDENT)
            .sample();
        // when
        // then
        Assertions.assertThatCode(() -> registerValidateService.validateAuthorityChangeable(mockRegister, Authority.PRESIDENT)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사용자 권한 변경 가능 여부 테스트 - 사용자가 운영진이 아니고 권한 파라미터가 운영진인 경우 UnchangeableException 발생")
    void validateAuthorityChangeable_with_member_and_president() {
        // given
        final Register mockRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("isRegistered", true)
            .set("authority", Authority.MEMBER)
            .sample();
        // when
        // then
        Assertions.assertThatCode(() -> registerValidateService.validateAuthorityChangeable(mockRegister, Authority.PRESIDENT)).isInstanceOf(UnchangeableException.class);
    }


}