package com.pawith.tododomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.exception.UnregistrableException;
import com.pawith.tododomain.repository.RegisterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;

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



}