package com.pawith.todoapplication.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.response.RegisterInfoResponse;
import com.pawith.todoapplication.dto.response.RegisterTermResponse;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("RegistersGetUseCase 테스트")
public class RegistersGetUseCaseTest {

    @Mock
    private UserUtils userUtils;
    @Mock
    private RegisterQueryService registerQueryService;
    @Mock
    private UserQueryService userQueryService;

    private RegistersGetUseCase registersGetUseCase;

    @BeforeEach
    void init() {
        registersGetUseCase = new RegistersGetUseCase(userUtils, registerQueryService, userQueryService);
    }

    @Test
    @DisplayName("Registers 조회 테스트")
    void getRegisters() {
        // given
        final User mockFindUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeOne(User.class);
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final List<Register> registerList = List.of(FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("userId", mockFindUser.getId())
            .set("todoTeamId", todoTeamId)
            .set("isRegistered", true)
            .sample()
        );
        final List<Long> userIds = registerList.stream().map(Register::getUserId).collect(Collectors.toList());
        given(registerQueryService.findAllRegistersByTodoTeamId(todoTeamId)).willReturn(registerList);
        given(userQueryService.findMapWithUserIdKeyByIds(userIds)).willReturn(Map.of(mockFindUser.getId(), mockFindUser));
        // when
        ListResponse<RegisterInfoResponse> registerInfoListResponse = registersGetUseCase.getRegisters(todoTeamId);
        // then
        Assertions.assertThat(registerInfoListResponse).isNotNull();
        Assertions.assertThat(registerInfoListResponse.getContent().size()).isEqualTo(registerList.size());
    }

    @Test
    @DisplayName("Register 기간 조회 테스트")
    void getRegisterTerm() {
        // given
        final User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeOne(User.class);
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final Integer registerTerm = FixtureMonkey.create().giveMeOne(Integer.class);
        given(userUtils.getAccessUser()).willReturn(mockUser);
        given(registerQueryService.findUserRegisterTerm(todoTeamId, mockUser.getId())).willReturn(registerTerm);
        // when
        RegisterTermResponse registerTermResponse = registersGetUseCase.getRegisterTerm(todoTeamId);
        // then
        Assertions.assertThat(registerTermResponse).isNotNull();
    }


}
