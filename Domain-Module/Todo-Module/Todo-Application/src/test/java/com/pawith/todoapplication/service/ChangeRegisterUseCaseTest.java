package com.pawith.todoapplication.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.service.RegisterQueryService;
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
    private ChangeRegisterUseCase changeRegisterUseCase;

    @BeforeEach
    void init() {
        changeRegisterUseCase = new ChangeRegisterUseCase(registerQueryService);
    }

    @Test
    @DisplayName("Register 권한 변경 테스트")
    void changeAuthority() {
        // given
        final Register mockRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Register.class);
        final Long registerId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final String authority = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Authority.class).toString();
        given(registerQueryService.findRegisterById(registerId)).willReturn(mockRegister);
        // when
        changeRegisterUseCase.changeAuthority(registerId, authority);
        // then
        Assertions.assertThat(mockRegister.getAuthority()).isEqualTo(Authority.valueOf(authority));
    }
}
