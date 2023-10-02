package com.pawith.tododomain.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.exception.TodoTeamNotFoundException;
import com.pawith.tododomain.repository.TodoTeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("TodoTeamQueryService 테스트")
class TodoTeamQueryServiceTest {

    @Mock
    private TodoTeamRepository todoTeamRepository;

    private TodoTeamQueryService todoTeamQueryService;

    @BeforeEach
    void init() {
        todoTeamQueryService = new TodoTeamQueryService(todoTeamRepository);
    }

    @Test
    @DisplayName("todoTeamId로 TodoTeam을 조회한다.")
    void findTodoTeamById() {
        //given
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final TodoTeam mockTodoTeam = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeOne(TodoTeam.class);
        given(todoTeamRepository.findById(todoTeamId)).willReturn(Optional.of(mockTodoTeam));
        //when
        TodoTeam todoTeam = todoTeamQueryService.findTodoTeamById(todoTeamId);
        //then
        Assertions.assertThat(todoTeam).usingRecursiveComparison().isEqualTo(mockTodoTeam);
    }

    @Test
    @DisplayName("todoTeamId로 TodoTeam을 조회할 수 없으면 TodoTeamNotFoundException 예외가 발생한다.")
    void findTodoTeamByIdThrowException() {
        //given
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        given(todoTeamRepository.findById(todoTeamId)).willReturn(Optional.empty());
        //when
        //then
        Assertions.assertThatCode(() -> todoTeamQueryService.findTodoTeamById(todoTeamId))
            .isInstanceOf(TodoTeamNotFoundException.class);
    }

    @Test
    @DisplayName("teamCode로 TodoTeam을 조회한다.")
    void findTodoTeamByTeamCode() {
        //given
        final String teamCode = UUID.randomUUID().toString().split("-")[0];
        final TodoTeam mockTodoTeam = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeOne(TodoTeam.class);
        given(todoTeamRepository.findByTeamCode(teamCode)).willReturn(Optional.of(mockTodoTeam));
        //when
        TodoTeam todoTeam = todoTeamQueryService.findTodoTeamByCode(teamCode);
        //then
        Assertions.assertThat(todoTeam).usingRecursiveComparison().isEqualTo(mockTodoTeam);
    }

    @Test
    @DisplayName("teamCode로 TodoTeam을 조회할 수 없으면 TodoTeamNotFoundException 예외가 발생한다.")
    void findTodoTeamByTeamCodeThrowException() {
        //given
        final String teamCode = UUID.randomUUID().toString().split("-")[0];
        given(todoTeamRepository.findByTeamCode(teamCode)).willReturn(Optional.empty());
        //when
        //then
        Assertions.assertThatCode(() -> todoTeamQueryService.findTodoTeamByCode(teamCode))
            .isInstanceOf(TodoTeamNotFoundException.class);
    }

    @Test
    @DisplayName("userId로 TodoTeam을 조회한다.")
    void findAllTodoTeamByUserId() {
        //given
        final Long userId = FixtureMonkey.create().giveMeOne(Long.class);
        final List<TodoTeam> mockTodoTeam = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(TodoTeam.class, 10);
        given(todoTeamRepository.findAllByUserId(userId)).willReturn(mockTodoTeam);
        //when
        List<TodoTeam> result = todoTeamQueryService.findAllTodoTeamByUserId(userId);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockTodoTeam);
    }


}