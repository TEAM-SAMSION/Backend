package com.pawith.todoapplication.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.tododomain.service.*;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

@UnitTestConfig
@DisplayName("TodoGetUseCase 테스트")
class TodoGetUseCaseTest {

    @Mock
    private UserUtils userUtils;
    @Mock
    private UserQueryService userQueryService;
    @Mock
    private AssignQueryService assignQueryService;
    @Mock
    private TodoNotificationQueryService todoNotificationQueryService;

    private TodoGetUseCase todoGetUseCase;

    @BeforeEach
    void init(){
        todoGetUseCase = new TodoGetUseCase(userUtils, userQueryService, assignQueryService, todoNotificationQueryService);
    }

//    @Test
//    @DisplayName("당일 할당된 TODO 조회 테스트")
//    void getTodos() {
//        // given
//        final Pageable mockPageable = PageRequest.of(0,10);
//        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
//        final User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
//            .giveMeOne(User.class);
//        final List<Todo> todoList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
//            .giveMe(Todo.class, mockPageable.getPageSize());
//        final Slice<Todo> todos = new SliceImpl<>(todoList, mockPageable, true);
//        given(userUtils.getAccessUser()).willReturn(mockUser);
//        given(todoQueryService.findTodayTodoSlice(mockUser.getId(), todoTeamId, mockPageable)).willReturn(todos);
//        // when
//        SliceResponse<TodoInfoResponse> result = todoGetUseCase.getTodoListByTodoTeamId(todoTeamId, mockPageable);
//        // then
//        Assertions.assertThat(result).isNotNull();
//        Assertions.assertThat(result.getContent().size()).isEqualTo(todoList.size());
//    }

}