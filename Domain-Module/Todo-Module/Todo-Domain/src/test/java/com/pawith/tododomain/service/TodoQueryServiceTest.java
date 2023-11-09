package com.pawith.tododomain.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.repository.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("TodoQueryService 테스트")
class TodoQueryServiceTest {

    @Mock
    private TodoRepository todoRepository;

    private TodoQueryService todoQueryService;

    @BeforeEach
    void init() {
        todoQueryService = new TodoQueryService(todoRepository);
    }

    @Test
    @DisplayName("Todo를 조회한다.")
    void findTodoByTodoId() {
        //given
        final Long todoId = FixtureMonkey.create().giveMeOne(Long.class);
        final Todo mockTodo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeOne(Todo.class);
        given(todoRepository.findById(todoId)).willReturn(Optional.of(mockTodo));
        //when
        Todo result = todoQueryService.findTodoByTodoId(todoId);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockTodo);
    }

    @Test
    @DisplayName("Todo를 조회할 수 없으면 예외가 발생한다.")
    void findTodoByTodoIdThrowException() {
        //given
        final Long todoId = FixtureMonkey.create().giveMeOne(Long.class);
        given(todoRepository.findById(todoId)).willReturn(Optional.empty());
        //when
        //then
        Assertions.assertThatCode(() -> todoQueryService.findTodoByTodoId(todoId))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Todo의 완료율을 조회한다.")
    void findTodoCompleteRate() {
        //given
        final Long userId = FixtureMonkey.create().giveMeOne(Long.class);
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final LocalDate now = LocalDate.now();
        final Long mockCountTodayTodo = FixtureMonkey.create().giveMeOne(Long.class);
        final Long mockCountCompleteTodayTodo = FixtureMonkey.create().giveMeOne(Long.class);
        given(todoRepository.countTodoByDate(userId, todoTeamId, now)).willReturn(mockCountTodayTodo);
        given(todoRepository.countCompleteTodoByDate(userId, todoTeamId, now)).willReturn(mockCountCompleteTodayTodo);
        //when
        Integer result = todoQueryService.findTodoCompleteRate(userId, todoTeamId);
        //then
        Assertions.assertThat(result).isEqualTo((int)(mockCountCompleteTodayTodo / (double) mockCountTodayTodo * 100));
    }

    @Test
    @DisplayName("오늘 할당받은 Todo를 조회한다.")
    void findTodayTodoList() {
        //given
        final Long userId = FixtureMonkey.create().giveMeOne(Long.class);
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final LocalDate now = LocalDate.now();
        final List<Todo> mockTodoList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Todo.class, 10);
        given(todoRepository.findTodoByDate(userId, todoTeamId, now)).willReturn(mockTodoList);
        //when
        List<Todo> result = todoQueryService.findTodayTodoList(userId, todoTeamId);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockTodoList);
    }

    @Test
    @DisplayName("categoryId와 moveDate로 Todo를 조회한다.")
    void findTodoListByCategoryIdAndscheduledDate() {
        //given
        final Long categoryId = FixtureMonkey.create().giveMeOne(Long.class);
        final LocalDate moveDate = FixtureMonkey.create().giveMeOne(LocalDate.class);
        final List<Todo> mockTodoList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Todo.class, 10);
        given(todoRepository.findTodoListByCategoryIdAndscheduledDate(categoryId, moveDate)).willReturn(mockTodoList);
        //when
        List<Todo> result = todoQueryService.findTodoListByCategoryIdAndscheduledDate(categoryId, moveDate);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockTodoList);
    }

    @Test
    @DisplayName("이번 주의 Todo의 완료율을 조회한다.")
    void findThisWeekTodoCompleteRate() {
        //given
        final Long userId = FixtureMonkey.create().giveMeOne(Long.class);
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final LocalDate now = LocalDate.now();
        final LocalDate firstDayOfWeek = now.with(java.time.DayOfWeek.SUNDAY);
        final Long mockCountWeekTodo = FixtureMonkey.create().giveMeOne(Long.class);
        final Long mockCountCompleteWeekTodo = FixtureMonkey.create().giveMeOne(Long.class);
        given(todoRepository.countTodoByBetweenDate(userId, todoTeamId, now, firstDayOfWeek)).willReturn(mockCountWeekTodo);
        given(todoRepository.countCompleteTodoByBetweenDate(userId, todoTeamId, now, firstDayOfWeek)).willReturn(mockCountCompleteWeekTodo);
        //when
        Integer result = todoQueryService.findThisWeekTodoCompleteRate(userId, todoTeamId);
        //then
        Assertions.assertThat(result).isEqualTo((int)(mockCountCompleteWeekTodo / (double) mockCountWeekTodo * 100));
    }

    @Test
    @DisplayName("지난 주의 Todo의 완료율을 조회한다.")
    void findLastWeekTodoCompleteRate() {
        //given
        final Long userId = FixtureMonkey.create().giveMeOne(Long.class);
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final LocalDate now = LocalDate.now();
        final LocalDate firstDayOfLastWeek = now.with(java.time.DayOfWeek.SUNDAY).minusWeeks(1);
        final Long mockCountWeekTodo = FixtureMonkey.create().giveMeOne(Long.class);
        final Long mockCountCompleteWeekTodo = FixtureMonkey.create().giveMeOne(Long.class);
        given(todoRepository.countTodoByBetweenDate(userId, todoTeamId, now, firstDayOfLastWeek)).willReturn(mockCountWeekTodo);
        given(todoRepository.countCompleteTodoByBetweenDate(userId, todoTeamId, now, firstDayOfLastWeek)).willReturn(mockCountCompleteWeekTodo);
        //when
        Integer result = todoQueryService.findLastWeekTodoCompleteRate(userId, todoTeamId);
        //then
        Assertions.assertThat(result).isEqualTo((int)(mockCountCompleteWeekTodo / (double) mockCountWeekTodo * 100));
    }


}