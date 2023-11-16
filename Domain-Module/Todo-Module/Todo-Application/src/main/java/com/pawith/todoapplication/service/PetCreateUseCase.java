package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.imagemodule.service.ImageUploadService;
import com.pawith.todoapplication.dto.request.PetRegisterRequest;
import com.pawith.todoapplication.mapper.PetMapper;
import com.pawith.tododomain.entity.Pet;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.service.PetSaveService;
import com.pawith.tododomain.service.TodoTeamQueryService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class PetCreateUseCase {
    private final PetSaveService petSaveService;
    private final TodoTeamQueryService todoTeamQueryService;
    private final ImageUploadService imageUploadService;

    public void createPet(Long todoTeamId, MultipartFile petImageFile, PetRegisterRequest petRegisterRequest) {
        final TodoTeam todoTeam = todoTeamQueryService.findTodoTeamById(todoTeamId);
        CompletableFuture<String> petImageAsync = imageUploadService.uploadImgAsync(petImageFile);
        final Pet pet = PetMapper.mapToPet(petRegisterRequest, todoTeam, petImageAsync.join());
        petSaveService.savePetEntity(pet);
    }
}
