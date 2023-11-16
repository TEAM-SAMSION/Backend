package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.tododomain.service.PetDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class PetDeleteUseCase {

    private final PetDeleteService petDeleteService;

    public void deletePet(Long petId) {
        petDeleteService.deletePet(petId);
    }
}
