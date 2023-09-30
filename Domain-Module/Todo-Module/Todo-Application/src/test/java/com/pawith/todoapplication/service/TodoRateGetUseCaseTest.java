package com.pawith.todoapplication.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.response.TodoProgressResponse;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("TodoRateGetUseCase 테스트")
class TodoRateGetUseCaseTest {

    @Mock
    private UserUtils userUtils;
    @Mock
    private TodoQueryService todoQueryService;

    private TodoRateGetUseCase todoRateGetUseCase;

    @BeforeEach
    void init(){
        todoRateGetUseCase = new TodoRateGetUseCase(userUtils, todoQueryService);
    }

    @Test
    @DisplayName("당일 할당된 TODO 완료율 조회 테스트")
    void getTodoProgress() {
        // given
        final User mockUser = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeOne(User.class);
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final Integer todoCompleteRate = (int) (Math.random() * 100);
        given(userUtils.getAccessUser()).willReturn(mockUser);
        given(todoQueryService.findTodoCompleteRate(mockUser.getId(), todoTeamId)).willReturn(todoCompleteRate);
        // when
        TodoProgressResponse todoProgress = todoRateGetUseCase.getTodoProgress(todoTeamId);
        // then
        Assertions.assertThat(todoProgress).isNotNull();
        Assertions.assertThat(todoProgress.getProgress()).isEqualTo(todoCompleteRate);
    }

}