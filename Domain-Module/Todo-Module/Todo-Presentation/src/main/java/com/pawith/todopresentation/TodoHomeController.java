package com.pawith.todopresentation;

import com.pawith.todoapplication.dto.response.TodoProgressResponse;
import com.pawith.todoapplication.dto.response.TodoTeamNameSimpleResponse;
import com.pawith.todoapplication.service.TodoRateGetUseCase;
import com.pawith.todoapplication.service.TodoTeamGetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo/home")
public class TodoHomeController {

    private final TodoRateGetUseCase todoRateGetUseCase;
    private final TodoTeamGetUseCase todoTeamGetUseCase;

    @GetMapping
    public List<TodoTeamNameSimpleResponse> getTodoTeamName() {return todoTeamGetUseCase.getTodoTeamName();}
    @GetMapping("/progress/{teamId}")
    public TodoProgressResponse getTodoProgress(@PathVariable Long teamId) { return todoRateGetUseCase.getTodoProgress(teamId);}
}
