package com.pawith.tododomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.repository.RegisterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.then;

@UnitTestConfig
@DisplayName("RegisterDeleteService 테스트")
class RegisterDeleteServiceTest {

    @Mock
    private RegisterRepository registerRepository;

    private RegisterDeleteService registerDeleteService;

    @BeforeEach
    void init() {
        registerDeleteService = new RegisterDeleteService(registerRepository);
    }

    @Test
    @DisplayName("Register 엔티티를 삭제한다.")
    void deleteRegisterByTodoTeamId() {
        //given
        final Register mockRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Register.class);
        //when
        registerDeleteService.deleteRegister(mockRegister);
        //then
        then(registerRepository).should().delete(mockRegister);
    }

}