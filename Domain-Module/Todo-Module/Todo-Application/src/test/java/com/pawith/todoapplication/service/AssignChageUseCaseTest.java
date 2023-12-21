package com.pawith.todoapplication.service;

import static org.mockito.BDDMockito.given;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.CompletionStatus;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.AssignDeleteService;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.AssignSaveService;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;

@UnitTestConfig
@DisplayName("AssignChageUseCase 테스트")
public class AssignChageUseCaseTest {

    @Mock
    AssignQueryService assignQueryService;
    @Mock
    TodoQueryService todoQueryService;
    @Mock
    AssignDeleteService assignDeleteService;
    @Mock
    AssignSaveService assignSaveService;
    @Mock
    RegisterQueryService registerQueryService;
    @Mock
    private UserUtils userUtils;
    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    private AssignChangeUseCase assignChangeUseCase;

    @BeforeEach
    void init(){
        assignChangeUseCase = new AssignChangeUseCase(assignQueryService, todoQueryService, assignDeleteService, assignSaveService, registerQueryService, userUtils, applicationEventPublisher);
    }

    @Test
    @DisplayName("담당자 Todo 완료 여부 변경 테스트")
    void changeAssignStatus() {
        // given
        final User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
                .giveMeOne(User.class);
        final Todo mockTodo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
                .giveMeOne(Todo.class);
        final Assign mockAssign = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Assign.class)
                .set("userId", mockUser.getId())
                .set("todoId", mockTodo.getId())
                .set("completionStatus", CompletionStatus.INCOMPLETE)
                .sample();
        final Long todoId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey()
                .giveMeOne(Long.class);
        given(userUtils.getAccessUser()).willReturn(mockUser);
        given(todoQueryService.findTodoByTodoId(todoId)).willReturn(mockTodo);
        given(assignQueryService.findAssignByTodoIdAndUserId(mockTodo.getId(), mockUser.getId())).willReturn(mockAssign);
        // when
        assignChangeUseCase.changeAssignStatus(todoId);
        // then
        Assertions.assertEquals(CompletionStatus.COMPLETE, mockAssign.getCompletionStatus());
    }

}
