package com.pawith.tododomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@UnitTestConfig
@DisplayName("TodoSaveService 테스트")
public class TodoSaveServiceTest {

    @Mock
    private TodoRepository todoRepository;

    private TodoSaveService todoSaveService;

    @BeforeEach
    void init() { todoSaveService = new TodoSaveService(todoRepository); }

    @Test
    @DisplayName("todo 엔티티를 입력받아 저장한다.")
    void saveTodoEntity() {
        //given
        final Todo todo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Todo.class);
        //when
        todoSaveService.saveTodoEntity(todo);
        //then
        then(todoRepository).should().save(any());
    }

}
