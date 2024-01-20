package com.pawith.todoapplication.handler;

import static org.mockito.BDDMockito.given;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.todoapplication.handler.event.TodoAssignStatusChangeEvent;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.CompletionStatus;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

@UnitTestConfig
@DisplayName("TodoCompletionCheckOnTodoHandler 테스트")
public class TodoCompletionCheckOnTodoHandlerTest {

    @Mock
    AssignQueryService assignQueryService;
    @Mock
    TodoQueryService todoQueryService;
    private TodoCompletionCheckOnTodoHandler todoCompletionCheckOnTodoHandler;

    @BeforeEach
    void init(){
        todoCompletionCheckOnTodoHandler = new TodoCompletionCheckOnTodoHandler(assignQueryService, todoQueryService);
    }

    @Test
    @DisplayName("Todo 완료 여부 변경 테스트-담당자가 모두 완료하면 Todo 완료")
    void changeTodoStatus() throws InterruptedException {
        // given
        final TodoAssignStatusChangeEvent todoAssignStatusChangeEvent = FixtureMonkeyUtils.getConstructBasedFixtureMonkey()
                .giveMeOne(TodoAssignStatusChangeEvent.class);
        final List<Assign> mockAssigns = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
                .giveMeBuilder(Assign.class)
                .set("completionStatus", CompletionStatus.COMPLETE)
                .sampleList(3);
        final Todo mockTodo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
                .giveMeBuilder(Todo.class)
                .set("completionStatus", CompletionStatus.INCOMPLETE)
                .sample();
        given(assignQueryService.findAllAssignByTodoId(todoAssignStatusChangeEvent.todoId())).willReturn(mockAssigns);
        given(todoQueryService.findTodoByTodoId(todoAssignStatusChangeEvent.todoId())).willReturn(mockTodo);
        // when
        todoCompletionCheckOnTodoHandler.changeTodoStatus(todoAssignStatusChangeEvent);
        // then
        Assertions.assertEquals(CompletionStatus.COMPLETE, mockTodo.getCompletionStatus());
    }

    @Test
    @DisplayName("Todo 완료 여부 변경 테스트-담당자가 하나라도 미완료면 Todo 미완료")
    void changeTodoStatusWithIncompletedAssignee() throws InterruptedException {
        // given
        final TodoAssignStatusChangeEvent todoAssignStatusChangeEvent = FixtureMonkeyUtils.getConstructBasedFixtureMonkey()
                .giveMeOne(TodoAssignStatusChangeEvent.class);
        final List<Assign> mockAssigns = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
                .giveMeBuilder(Assign.class)
                .set("completionStatus", CompletionStatus.COMPLETE)
                .sampleList(2);
        final Assign mockAssign = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
                .giveMeBuilder(Assign.class)
                .set("completionStatus", CompletionStatus.INCOMPLETE)
                .sample();
        mockAssigns.add(mockAssign);
        final Todo mockTodo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
                .giveMeBuilder(Todo.class)
                .set("completionStatus", CompletionStatus.INCOMPLETE)
                .sample();
        given(assignQueryService.findAllAssignByTodoId(todoAssignStatusChangeEvent.todoId())).willReturn(mockAssigns);
        given(todoQueryService.findTodoByTodoId(todoAssignStatusChangeEvent.todoId())).willReturn(mockTodo);
        // when
        todoCompletionCheckOnTodoHandler.changeTodoStatus(todoAssignStatusChangeEvent);
        // then
        Assertions.assertEquals(CompletionStatus.INCOMPLETE, mockTodo.getCompletionStatus());
    }

}
