package com.pawith.todopresentation;

import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.response.TodoTeamSimpleResponse;
import com.pawith.todoapplication.service.TodoTeamGetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo/team")
public class TodoTeamController {
    private final TodoTeamGetUseCase todoTeamGetUseCase;

    @GetMapping("/list")
    public SliceResponse<TodoTeamSimpleResponse> getTodoTeams(Pageable pageable) {
        return todoTeamGetUseCase.getTodoTeams(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending()));
    }
}
