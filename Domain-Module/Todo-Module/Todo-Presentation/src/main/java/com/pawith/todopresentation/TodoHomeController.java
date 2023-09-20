package com.pawith.todopresentation;

import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.response.TodoHomeResponse;
import com.pawith.todoapplication.dto.response.TodoProgressResponse;
import com.pawith.todoapplication.dto.response.TodoTeamNameSimpleResponse;
import com.pawith.todoapplication.service.TodoGetUseCase;
import com.pawith.todoapplication.service.TodoRateGetUseCase;
import com.pawith.todoapplication.service.TodoTeamGetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo/home")
public class TodoHomeController {

    private final TodoRateGetUseCase todoRateGetUseCase;
    private final TodoTeamGetUseCase todoTeamGetUseCase;
    private final TodoGetUseCase todoGetUseCase;

    @GetMapping
    public List<TodoTeamNameSimpleResponse> getTodoTeamName() {return todoTeamGetUseCase.getTodoTeamName();}
    @GetMapping("/progress/{teamId}")
    public TodoProgressResponse getTodoProgress(@PathVariable Long teamId) { return todoRateGetUseCase.getTodoProgress(teamId);}

    @GetMapping("/list/{teamId}")
    public SliceResponse<TodoHomeResponse> getTodos(@PathVariable Long teamId, Pageable pageable) { return todoGetUseCase.getTodos(teamId, pageable);}
}
