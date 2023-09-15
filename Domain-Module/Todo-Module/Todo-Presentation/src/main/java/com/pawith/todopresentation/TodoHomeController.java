package com.pawith.todopresentation;

import com.pawith.todoapplication.dto.response.TodoProgressResponse;
import com.pawith.todoapplication.service.TodoRateGetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo/home")
public class TodoHomeController {

    private final TodoRateGetUseCase todoRateGetUseCase;

    @GetMapping("/progress/{teamId}")
    public TodoProgressResponse getTodoProgress(@PathVariable Long teamId) { return todoRateGetUseCase.getTodoProgress(teamId);}
}
