package com.pawith.todopresentation;

import com.pawith.commonmodule.response.ListResponse;
import com.pawith.todoapplication.dto.response.PetInfoResponse;
import com.pawith.todoapplication.service.PetGetUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class PetController {
    private final PetGetUseCase petGetUseCase;

    @GetMapping("/{todoTeamId}/pet")
    public ListResponse<PetInfoResponse> getTodoTeamPets(@PathVariable Long todoTeamId) {
        return petGetUseCase.getTodoTeamPets(todoTeamId);
    }
}
