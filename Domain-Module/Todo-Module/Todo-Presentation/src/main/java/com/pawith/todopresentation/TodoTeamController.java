package com.pawith.todopresentation;

import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.response.TodoTeamSimpleResponse;
import com.pawith.todoapplication.service.TodoTeamGetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo/team")
public class TodoTeamController {
    private final TodoTeamGetUseCase todoTeamGetUseCase;

    @GetMapping("/list")
    public SliceResponse<TodoTeamSimpleResponse> getTodoTeams(Pageable pageable) {
        return todoTeamGetUseCase.getTodoTeams(pageable);
    }
}
