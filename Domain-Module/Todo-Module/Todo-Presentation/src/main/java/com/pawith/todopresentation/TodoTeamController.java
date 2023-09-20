package com.pawith.todopresentation;

import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.request.TodoTeamCreateRequest;
import com.pawith.todoapplication.dto.response.TodoTeamRandomCodeResponse;
import com.pawith.todoapplication.dto.response.TodoTeamSimpleResponse;
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
@RequestMapping("/todo/team")
public class TodoTeamController {
    private final TodoTeamGetUseCase todoTeamGetUseCase;
    private final TodoTeamRandomCodeGetUseCase todoTeamRandomCodeGetUseCase;
    private final TodoTeamCreateUseCase todoTeamCreateUseCase;

    @GetMapping("/list")
    public SliceResponse<TodoTeamSimpleResponse> getTodoTeams(Pageable pageable) {
        return todoTeamGetUseCase.getTodoTeams(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending()));
    }

    @GetMapping("/code")
    public TodoTeamRandomCodeResponse getTodoTeamRandomCode(){
        return todoTeamRandomCodeGetUseCase.generateRandomCode();
    }

    /**
     * 리팩터링 전 : 이미지 업로드 API + 팀 생성 API 100회 테스트 평균 : 502ms
     * <br>리팩터링 후 : 테스트 예정
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void postTodoTeam(@RequestPart("imageFiles") List<MultipartFile> imageFiles,
                             @RequestPart("todoTeamCreateInfo") TodoTeamCreateRequest todoTeamCreateInfo){
        todoTeamCreateUseCase.createTodoTeam(imageFiles,todoTeamCreateInfo);
    }
}
