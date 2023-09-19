package com.pawith.tododomain.service;

import com.pawith.commonmodule.UnitTestConfig;
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
class TodoTeamCodeGenerateServiceTest {

    @Mock
    private TodoTeamRepository todoTeamRepository;

    private TodoTeamCodeGenerateService todoTeamCodeGenerateService;

    @BeforeEach
    void init() {
        todoTeamCodeGenerateService = new TodoTeamCodeGenerateService(todoTeamRepository);
    }

    @Test
    @DisplayName("TodoTeamCode를 생성한다.")
    void generateTodoTeamCode() {
        //given
        given(todoTeamRepository.existsByTeamCode(anyString())).willReturn(false);
        //when
        String result = todoTeamCodeGenerateService.generateRandomCode();
        //then
        assertEquals(8, result.length());
    }
}