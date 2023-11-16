package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.imagemodule.service.ImageUploadService;
import com.pawith.todoapplication.dto.request.PetInfoChangeRequest;
import com.pawith.tododomain.entity.Pet;
import com.pawith.tododomain.service.PetQueryService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class PetChangeUseCase {
    private final PetQueryService petQueryService;
    private final ImageUploadService imageUploadService;

    public void updatePet(Long petId, MultipartFile teamImageFile, PetInfoChangeRequest request) {
        CompletableFuture<String> teamImageAsync = imageUploadService.uploadImgAsync(teamImageFile);
        Pet pet = petQueryService.findPetById(petId);
        pet.updatePet(teamImageAsync.join(), request.getName(), request.getAge(), request.getGenus(), request.getSpecies(), request.getDescription());
    }

}
