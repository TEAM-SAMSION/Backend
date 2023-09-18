package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.response.TodoTeamRandomCodeResponse;
import com.pawith.tododomain.service.TodoTeamCodeGenerateService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class TodoTeamRandomCodeGetUseCase {

    private final TodoTeamCodeGenerateService todoTeamCodeGenerateService;

    public TodoTeamRandomCodeResponse generateRandomCode() {
        return new TodoTeamRandomCodeResponse(todoTeamCodeGenerateService.generateRandomCode());
    }
}
