package com.pawith.todoapplication.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.request.ScheduledDateChangeRequest;
import com.pawith.todoapplication.dto.request.TodoDescriptionChangeRequest;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.TodoQueryService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.mockito.BDDMockito.given;

@Slf4j
@UnitTestConfig
@DisplayName("TodoChangeUseCase 테스트")
public class TodoChangeUseCaseTest {

    @Mock
    private TodoQueryService todoQueryService;

    private TodoChangeUseCase todoChangeUseCase;

    @BeforeEach
    void init() {
        todoChangeUseCase = new TodoChangeUseCase(todoQueryService);
    }

    @Test
    @DisplayName("Todo의 예정일을 변경 테스트")
    void changeScheduledDate() {
        //given
        Long todoId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        LocalDate currentDate = LocalDate.now();
        LocalDate nextDay = currentDate.plus(1, ChronoUnit.DAYS);
        ScheduledDateChangeRequest request = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(ScheduledDateChangeRequest.class)
            .set("scheduledDate", nextDay)
            .sample();
        Todo mockTodo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Todo.class);
        given(todoQueryService.findTodoByTodoId(todoId)).willReturn(mockTodo);
        //when
        todoChangeUseCase.changeScheduledDate(todoId, request);
        //then
        Assertions.assertThat(mockTodo.getScheduledDate()).isEqualTo(nextDay);
    }

    @Test
    @DisplayName("Todo의 예정일을 변경 테스트 - 현재 날짜보다 이전 날짜로 변경하면 예외 발생")
    void changeWrongScheduledDate() {
        //given
        Long todoId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        LocalDate currentDate = LocalDate.now();
        LocalDate nextDay = currentDate.minus(1, ChronoUnit.DAYS);
        ScheduledDateChangeRequest request = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(ScheduledDateChangeRequest.class)
                .set("scheduledDate", nextDay)
                .sample();
        //when
        //then
        Assertions.assertThatCode(() -> todoChangeUseCase.changeScheduledDate(todoId, request))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Todo의 이름 변경 테스트")
    void changeTodoName() {
        //given
        Long todoId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        TodoDescriptionChangeRequest request = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(TodoDescriptionChangeRequest.class);
        Todo mockTodo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Todo.class);
        given(todoQueryService.findTodoByTodoId(todoId)).willReturn(mockTodo);
        //when
        todoChangeUseCase.changeTodoName(todoId, request);
        //then
        Assertions.assertThat(mockTodo.getDescription()).isEqualTo(request.getDescription());
    }

}
