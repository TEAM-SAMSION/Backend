package com.pawith.todopresentation;

import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.response.TodoHomeResponse;
import com.pawith.todoapplication.dto.response.TodoProgressResponse;
import com.pawith.todoapplication.service.TodoGetUseCase;
import com.pawith.todoapplication.service.TodoRateGetUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoGetUseCase todoGetUseCase;
    private final TodoRateGetUseCase todoRateGetUseCase;

    /**
     * 리팩터링하기 전 : 동시 100명 접속 총 6000회 테스트, 평균 400ms
     */
    @GetMapping("/progress/{teamId}")
    public TodoProgressResponse getTodoProgress(@PathVariable Long teamId) {
        return todoRateGetUseCase.getTodoProgress(teamId);
    }


    @GetMapping("/list/{teamId}")
    public SliceResponse<TodoHomeResponse> getTodos(@PathVariable Long teamId, Pageable pageable) {
        return todoGetUseCase.getTodos(teamId, pageable);
    }
}
