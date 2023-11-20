package com.pawith.todoapplication.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.commonmodule.response.SliceResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.response.TodoTeamNameResponse;
import com.pawith.todoapplication.dto.response.TodoTeamRandomCodeResponse;
import com.pawith.todoapplication.dto.response.TodoTeamSearchInfoResponse;
import com.pawith.todoapplication.dto.response.TodoTeamInfoResponse;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.service.PetQueryService;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoTeamQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("TodoTeamGetUseCase 테스트")
public class TodoTeamGetUseCaseTest {

    @Mock
    private UserUtils userUtils;
    @Mock
    private RegisterQueryService registerQueryService;
    @Mock
    private TodoTeamQueryService todoTeamQueryService;
    @Mock
    private UserQueryService userQueryService;
    @Mock
    private PetQueryService petQueryService;

    private TodoTeamGetUseCase todoTeamGetUseCase;

    @BeforeEach
    void init() {
        todoTeamGetUseCase = new TodoTeamGetUseCase(userUtils, registerQueryService, todoTeamQueryService, userQueryService, petQueryService);
    }

    @Test
    @DisplayName("사용자가 가입한 TodoTeam 조회 테스트")
    void getTodoTeams() {
        // given
        final Pageable mockPageable = PageRequest.of(0,10);
        final User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(User.class);
        final List<TodoTeamInfoResponse> todoTeamInfoResponseList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(TodoTeamInfoResponse.class, mockPageable.getPageSize());
        final List<Register> registerList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Register.class, mockPageable.getPageSize());
        final Slice<Register> registers = new SliceImpl<>(registerList, mockPageable, true);
        given(userUtils.getAccessUser()).willReturn(mockUser);
        given(registerQueryService.findRegisterSliceByUserId(mockUser.getId(), mockPageable)).willReturn(registers);
        // when
        SliceResponse<TodoTeamInfoResponse> result = todoTeamGetUseCase.getTodoTeams(mockPageable);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getContent()).isNotNull();
        Assertions.assertThat(result.getContent().size()).isEqualTo(todoTeamInfoResponseList.size());
    }

    @Test
    @DisplayName("사용자가 가입한 TodoTeam 이름 조회 테스트")
    void getTodoTeamName() {
        // given
        final User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(User.class);
        final List<TodoTeam> todoTeamList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(TodoTeam.class, 10);
        final List<Register> registerList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Register.class, 10);
        given(userUtils.getAccessUser()).willReturn(mockUser);
        given(registerQueryService.findRegisterListByUserId(mockUser.getId())).willReturn(registerList);
        // when
        ListResponse<TodoTeamNameResponse> result = todoTeamGetUseCase.getTodoTeamName();
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getContent().size()).isEqualTo(todoTeamList.size());
    }

    @Test
    @DisplayName("TodoTeam 코드로 조회 테스트")
    void searchTodoTeamByCode() {
        // given
        final String code = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final TodoTeam todoTeam = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(TodoTeam.class);
        final Register presidentRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Register.class);
        final User presidentUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(User.class);
        final Integer registerCount = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Integer.class);
        given(todoTeamQueryService.findTodoTeamByCode(code)).willReturn(todoTeam);
        given(registerQueryService.findPresidentRegisterByTodoTeamId(todoTeam.getId())).willReturn(presidentRegister);
        given(userQueryService.findById(presidentRegister.getUserId())).willReturn(presidentUser);
        given(registerQueryService.countRegisterByTodoTeamId(todoTeam.getId())).willReturn(registerCount);
        // when
        TodoTeamSearchInfoResponse result = todoTeamGetUseCase.searchTodoTeamByCode(code);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getCode()).isEqualTo(todoTeam.getTeamCode());
        Assertions.assertThat(result.getTeamName()).isEqualTo(todoTeam.getTeamName());
        Assertions.assertThat(result.getPresidentName()).isEqualTo(presidentUser.getNickname());
        Assertions.assertThat(result.getRegisterCount()).isEqualTo(registerCount);
        Assertions.assertThat(result.getDescription()).isEqualTo(todoTeam.getDescription());
        Assertions.assertThat(result.getTeamImageUrl()).isEqualTo(todoTeam.getImageUrl());

    }

    @Test
    @DisplayName("TodoTeamId로 TodoTeam 코드 조회 테스트")
    void getTodoTeamCode() {
        // given
        final Long teamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final TodoTeam todoTeam = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(TodoTeam.class);
        given(todoTeamQueryService.findTodoTeamById(teamId)).willReturn(todoTeam);
        // when
        TodoTeamRandomCodeResponse result = todoTeamGetUseCase.getTodoTeamCode(teamId);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getRandomCode()).isEqualTo(todoTeam.getTeamCode());
    }
}