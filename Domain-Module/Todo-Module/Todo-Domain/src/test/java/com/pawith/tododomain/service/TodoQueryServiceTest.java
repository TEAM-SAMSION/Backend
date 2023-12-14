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
import org.springframework.data.domain.Pageable;
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
        given(todoRepository.countTodoByDateQuery(userId, todoTeamId, now)).willReturn(mockCountTodayTodo);
        given(todoRepository.countCompleteTodoByDateQuery(userId, todoTeamId, now)).willReturn(mockCountCompleteTodayTodo);
        //when
        Integer result = todoQueryService.findTodoCompleteRate(userId, todoTeamId);
        //then
        Assertions.assertThat(result).isEqualTo((int) (mockCountCompleteTodayTodo / (double) mockCountTodayTodo * 100));
    }

    @Test
    @DisplayName("categoryId와 moveDate로 Todo를 조회한다.")
    void findTodoListByCategoryIdAndscheduledDate() {
        //given
        final Long categoryId = FixtureMonkey.create().giveMeOne(Long.class);
        final LocalDate moveDate = FixtureMonkey.create().giveMeOne(LocalDate.class);
        final List<Todo> mockTodoList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Todo.class, 10);
        given(todoRepository.findTodoListByCategoryIdAndScheduledDateQuery(categoryId, moveDate)).willReturn(mockTodoList);
        //when
        List<Todo> result = todoQueryService.findTodoListByCategoryIdAndScheduledDate(categoryId, moveDate);
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
        given(todoRepository.countTodoByBetweenDateQuery(userId, todoTeamId, now, firstDayOfWeek)).willReturn(mockCountWeekTodo);
        given(todoRepository.countCompleteTodoByBetweenDateQuery(userId, todoTeamId, now, firstDayOfWeek)).willReturn(mockCountCompleteWeekTodo);
        //when
        Integer result = todoQueryService.findThisWeekTodoCompleteRate(userId, todoTeamId);
        //then
        Assertions.assertThat(result).isEqualTo((int) (mockCountCompleteWeekTodo / (double) mockCountWeekTodo * 100));
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
        given(todoRepository.countTodoByBetweenDateQuery(userId, todoTeamId, now, firstDayOfLastWeek)).willReturn(mockCountWeekTodo);
        given(todoRepository.countCompleteTodoByBetweenDateQuery(userId, todoTeamId, now, firstDayOfLastWeek)).willReturn(mockCountCompleteWeekTodo);
        //when
        Integer result = todoQueryService.findLastWeekTodoCompleteRate(userId, todoTeamId);
        //then
        Assertions.assertThat(result).isEqualTo((int) (mockCountCompleteWeekTodo / (double) mockCountWeekTodo * 100));
    }

    @Test
    @DisplayName("UserId와 TodoTeamId로 Todo 목록을 조회한다.")
    void findAllTodoListByTodoTeamId() {
        //given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Pageable pageable = PageRequest.of(0, 10);
        final List<Todo> mockTodoList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Todo.class, pageable.getPageSize());
        final SliceImpl<Todo> mockTodoSlice = new SliceImpl<>(mockTodoList, pageable, true);
        given(todoRepository.findTodoSliceByUserIdAndTodoTeamIdQuery(userId, todoTeamId, pageable)).willReturn(mockTodoSlice);
        //when
        Slice<Todo> result = todoQueryService.findAllTodoListByTodoTeamId(userId, todoTeamId, pageable);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockTodoSlice);
    }

    @Test
    @DisplayName("UserId로 Todo 목록을 조회한다.")
    void findAllTodoListByUserId() {
        //given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Pageable pageable = PageRequest.of(0, 10);
        final List<Todo> mockTodoList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Todo.class, pageable.getPageSize());
        final SliceImpl<Todo> mockTodoSlice = new SliceImpl<>(mockTodoList, pageable, true);
        given(todoRepository.findTodoSliceByUserIdQuery(userId, pageable)).willReturn(mockTodoSlice);
        //when
        Slice<Todo> result = todoQueryService.findAllTodoListByUserId(userId, pageable);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockTodoSlice);
    }

    @Test
    @DisplayName("UserId와 TodoTeamId로 Todo의 개수를 조회한다.")
    void countTodoByTodoTeamId() {
        //given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Integer mockCount = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Integer.class);
        given(todoRepository.countTodoByUserIdAndTodoTeamIdQuery(userId, todoTeamId)).willReturn(mockCount);
        //when
        Integer result = todoQueryService.countTodoByTodoTeamId(userId, todoTeamId);
        //then
        Assertions.assertThat(result).isEqualTo(mockCount);
    }

    @Test
    @DisplayName("UserId로 Todo의 개수를 조회한다.")
    void countTodoByUserId() {
        //given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Integer mockCount = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Integer.class);
        given(todoRepository.countTodoByUserIdQuery(userId)).willReturn(mockCount);
        //when
        Integer result = todoQueryService.countTodoByUserId(userId);
        //then
        Assertions.assertThat(result).isEqualTo(mockCount);
    }



}