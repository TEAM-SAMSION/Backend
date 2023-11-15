package com.pawith.todopresentation;

import com.pawith.commonmodule.response.ListResponse;
import com.pawith.commonmodule.response.SliceResponse;
import com.pawith.todoapplication.dto.request.TodoTeamCreateRequest;
import com.pawith.todoapplication.dto.response.TodoTeamInfoDetailResponse;
import com.pawith.todoapplication.dto.response.TodoTeamNameResponse;
import com.pawith.todoapplication.dto.response.TodoTeamRandomCodeResponse;
import com.pawith.todoapplication.dto.response.TodoTeamSearchInfoResponse;
import com.pawith.todoapplication.dto.response.TodoTeamInfoResponse;
import com.pawith.todoapplication.dto.response.WithdrawTodoTeamResponse;
import com.pawith.todoapplication.service.TodoTeamCreateUseCase;
import com.pawith.todoapplication.service.TodoTeamGetUseCase;
import com.pawith.todoapplication.service.TodoTeamRandomCodeGetUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TodoTeamController {
    private final TodoTeamGetUseCase todoTeamGetUseCase;
    private final TodoTeamRandomCodeGetUseCase todoTeamRandomCodeGetUseCase;
    private final TodoTeamCreateUseCase todoTeamCreateUseCase;

    @GetMapping
    public SliceResponse<TodoTeamInfoResponse> getTodoTeams(Pageable pageable) {
        return todoTeamGetUseCase.getTodoTeams(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending()));
    }

    @GetMapping("/codes/random")
    public TodoTeamRandomCodeResponse getTodoTeamRandomCode() {
        return todoTeamRandomCodeGetUseCase.generateRandomCode();
    }

    @GetMapping("/codes/{code}")
    public TodoTeamSearchInfoResponse getTodoTeamByCode(@PathVariable String code) {
        return todoTeamGetUseCase.searchTodoTeamByCode(code);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void postTodoTeam(@RequestPart("teamImageFile") MultipartFile teamImageFile,
                             @RequestPart("petimageFiles") List<MultipartFile> petImageFiles,
                             @RequestPart("todoTeamCreateInfo") TodoTeamCreateRequest todoTeamCreateInfo) {
        todoTeamCreateUseCase.createTodoTeam(teamImageFile,petImageFiles, todoTeamCreateInfo);
    }

    @GetMapping("/name")
    public ListResponse<TodoTeamNameResponse> getTodoTeamName() {
        return todoTeamGetUseCase.getTodoTeamName();
    }

    @GetMapping("/{todoTeamId}/codes")
    public TodoTeamRandomCodeResponse getTodoTeamCode(@PathVariable Long todoTeamId){
        return todoTeamGetUseCase.getTodoTeamCode(todoTeamId);
    }

    @GetMapping("/withdraw")
    public ListResponse<WithdrawTodoTeamResponse> getWithdrawTodoTeamList() {
        return todoTeamGetUseCase.getWithdrawTodoTeam();
    }

    @GetMapping("/{todoTeamId}")
    public TodoTeamInfoDetailResponse getTodoTeamInfo(@PathVariable Long todoTeamId) {
        return todoTeamGetUseCase.getTodoTeamInfo(todoTeamId);
    }
}
