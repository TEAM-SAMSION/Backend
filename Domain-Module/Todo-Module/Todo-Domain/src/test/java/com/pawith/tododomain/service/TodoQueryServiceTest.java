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

}