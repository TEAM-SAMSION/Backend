package com.pawith.tododomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.cache.operators.SetOperator;
import com.pawith.tododomain.repository.TodoTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("TodoTeamCodeGenerateService 테스트")
class TodoTeamCodeManageServiceTest {

    @Mock
    private TodoTeamRepository todoTeamRepository;
    @Mock
    private SetOperator<String> setOperator;

    private TodoTeamCodeManageService todoTeamCodeManageService;

    @BeforeEach
    void init() {
        todoTeamCodeManageService = new TodoTeamCodeManageService(todoTeamRepository, setOperator);
    }

    @Test
    @DisplayName("TodoTeamCode를 생성한다.")
    void generateTodoTeamCode() {
        //given
        given(todoTeamRepository.existsByTeamCode(anyString())).willReturn(false);
        //when
        String result = todoTeamCodeManageService.generateRandomCode();
        //then
        assertEquals(8, result.length());
    }
}