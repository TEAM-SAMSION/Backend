package com.pawith.todopresentation;

import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.request.TodoCreateRequest;
import com.pawith.todoapplication.dto.response.TodoHomeResponse;
import com.pawith.todoapplication.dto.response.TodoProgressResponse;
import com.pawith.todoapplication.service.TodoCreateUseCase;
import com.pawith.todoapplication.service.TodoGetUseCase;
import com.pawith.todoapplication.service.TodoRateGetUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoGetUseCase todoGetUseCase;
    private final TodoRateGetUseCase todoRateGetUseCase;
    private final TodoCreateUseCase todoCreateUseCase;

    /**
     * 리팩터링 전 : 동시 100명 요청 평균 426ms
     * <br>리팩터링 후 : 동시 100명 요청 평균 67ms(535% 개선)
     */
    @GetMapping("/progress/{teamId}")
    public TodoProgressResponse getTodoProgress(@PathVariable Long teamId) {
        return todoRateGetUseCase.getTodoProgress(teamId);
    }

    /**
     * 리팩터링 전 , 100명 동시 요청 테스트 평균 : 915ms
     * <br>리팩터링 후 , 100명 동시 요청 테스트 평균 : 85ms(914% 성능개선)
     */
    @GetMapping("/list/{teamId}")
    public SliceResponse<TodoHomeResponse> getTodos(@PathVariable Long teamId, Pageable pageable) {
        return todoGetUseCase.getTodos(teamId, pageable);
    }

    @PostMapping
    public void postTodo(@RequestBody TodoCreateRequest todoCreateRequest){
        todoCreateUseCase.createTodo(todoCreateRequest);
    }
}
