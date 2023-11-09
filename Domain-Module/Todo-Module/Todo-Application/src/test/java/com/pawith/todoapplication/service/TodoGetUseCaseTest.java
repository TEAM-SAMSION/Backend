package com.pawith.todoapplication.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.CategoryQueryService;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoQueryService;
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
    private TodoQueryService todoQueryService;
    @Mock
    private CategoryQueryService categoryQueryService;
    @Mock
    private UserQueryService userQueryService;
    @Mock
    private RegisterQueryService registerQueryService;
    @Mock
    private AssignQueryService assignQueryService;

    private TodoGetUseCase todoGetUseCase;

    @BeforeEach
    void init(){
        todoGetUseCase = new TodoGetUseCase(userUtils, todoQueryService, userQueryService, registerQueryService, assignQueryService);
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