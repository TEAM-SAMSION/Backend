package com.pawith.todopresentation;

import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.request.TodoCreateRequest;
import com.pawith.todoapplication.dto.response.*;
import com.pawith.todoapplication.service.RegistersGetUseCase;
import com.pawith.todoapplication.service.TodoCreateUseCase;
import com.pawith.todoapplication.service.TodoGetUseCase;
import com.pawith.todoapplication.service.TodoRateGetUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoGetUseCase todoGetUseCase;
    private final TodoRateGetUseCase todoRateGetUseCase;
    private final TodoCreateUseCase todoCreateUseCase;
    private final RegistersGetUseCase registersGetUseCase;

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
        return todoGetUseCase.getTodoListByTodoTeamId(teamId, pageable);
    }

    @PostMapping
    public void postTodo(@RequestBody TodoCreateRequest todoCreateRequest){
        todoCreateUseCase.createTodo(todoCreateRequest);
    }


    @GetMapping("/{categoryId}")
    public TodoListResponse getTodoList(@PathVariable Long categoryId, @RequestParam("moveDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate moveDate){
        return todoGetUseCase.getTodoListByCategoryId(categoryId, moveDate);
    }

    @GetMapping("/compare/{teamId}")
    public TodoRateCompareResponse getWeekProgressCompare(@PathVariable Long teamId){
        return todoRateGetUseCase.getWeekProgressCompare(teamId);
    }

    @GetMapping("/register/{teamId}")
    public RegisterTermResponse getRegisterTerm(@PathVariable Long teamId){
        return registersGetUseCase.getRegisterTerm(teamId);
    }

}
