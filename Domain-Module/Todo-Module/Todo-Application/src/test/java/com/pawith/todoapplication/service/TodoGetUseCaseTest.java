package com.pawith.todoapplication.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.response.TodoHomeResponse;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.service.CategoryQueryService;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoQueryService;
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

    private TodoGetUseCase todoGetUseCase;

    @BeforeEach
    void init(){
        todoGetUseCase = new TodoGetUseCase(userUtils, todoQueryService, categoryQueryService, userQueryService, registerQueryService);
    }

    @Test
    @DisplayName("당일 할당된 TODO 조회 테스트")
    void getTodos() {
        // given
        final Pageable mockPageable = PageRequest.of(0,10);
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeOne(User.class);
        final List<Todo> todoList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMe(Todo.class, mockPageable.getPageSize());
        final Slice<Todo> todos = new SliceImpl<>(todoList, mockPageable, true);
        given(userUtils.getAccessUser()).willReturn(mockUser);
        given(todoQueryService.findTodayTodoSlice(mockUser.getId(), todoTeamId, mockPageable)).willReturn(todos);
        // when
        SliceResponse<TodoHomeResponse> result = todoGetUseCase.getTodos(todoTeamId, mockPageable);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getContent().size()).isEqualTo(todoList.size());
    }

}