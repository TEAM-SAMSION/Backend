package com.pawith.todopresentation;

import com.pawith.commonmodule.response.ListResponse;
import com.pawith.todoapplication.dto.request.PetInfoChangeRequest;
import com.pawith.todoapplication.dto.request.PetRegisterRequest;
import com.pawith.todoapplication.dto.response.PetInfoResponse;
import com.pawith.todoapplication.service.PetChangeUseCase;
import com.pawith.todoapplication.service.PetCreateUseCase;
import com.pawith.todoapplication.service.PetDeleteUseCase;
import com.pawith.todoapplication.service.PetGetUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class PetController {
    private final PetCreateUseCase petCreateUseCase;
    private final PetGetUseCase petGetUseCase;
    private final PetDeleteUseCase petDeleteUseCase;
    private final PetChangeUseCase petChangeUseCase;

    @PostMapping("/{todoTeamId}/pets")
    public void postTodoTeamPet(@PathVariable Long todoTeamId, @RequestPart(value = "petImageFile") MultipartFile petImageFile,
                                @RequestPart(value = "petCreateInfo") PetRegisterRequest petRegisterRequest) {
        petCreateUseCase.createPet(todoTeamId, petImageFile, petRegisterRequest);
    }

    @GetMapping("/{todoTeamId}/pets")
    public ListResponse<PetInfoResponse> getTodoTeamPets(@PathVariable Long todoTeamId) {
        return petGetUseCase.getTodoTeamPets(todoTeamId);
    }

    @DeleteMapping("pets/{petId}")
    public void deleteTodoTeamPet(@PathVariable Long petId) {
        petDeleteUseCase.deletePet(petId);
    }

    @PostMapping("pets/{petId}")
    public void putTodoTeamPet(@PathVariable Long petId, @RequestPart(value = "petImageFile", required = false) MultipartFile petImageFile,
                               @RequestPart(value = "petUpdateInfo", required = false) PetInfoChangeRequest petInfoChangeRequest) {
        petChangeUseCase.updatePet(petId, petImageFile, petInfoChangeRequest);
    }
}
