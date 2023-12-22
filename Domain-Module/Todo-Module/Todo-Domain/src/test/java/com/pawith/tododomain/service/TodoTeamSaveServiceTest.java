package com.pawith.tododomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.repository.TodoTeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.BDDMockito.then;

@Slf4j
@UnitTestConfig
@DisplayName("TodoTeamSaveService 테스트")
class TodoTeamSaveServiceTest {

    @Mock
    private TodoTeamRepository todoTeamRepository;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private TodoTeamSaveService todoTeamSaveService;

    @BeforeEach
    void init() {
        todoTeamSaveService = new TodoTeamSaveService(todoTeamRepository, applicationEventPublisher);
    }

    @Test
    @DisplayName("TodoTeam을 저장한다.")
    void saveTodoTeamEntity() {
        //given
        final TodoTeam mockTodoTeam = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeOne(TodoTeam.class);
        log.info("mockTodoTeam: {}", mockTodoTeam);
        //when
        todoTeamSaveService.saveTodoTeamEntity(mockTodoTeam);
        //then
        then(todoTeamRepository).should().save(mockTodoTeam);
    }
}