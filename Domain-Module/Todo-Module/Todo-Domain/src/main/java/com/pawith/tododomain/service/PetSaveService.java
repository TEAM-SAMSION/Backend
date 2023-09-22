package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Pet;
import com.pawith.tododomain.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class PetSaveService {
    private final PetRepository petRepository;

    public void savePetEntity(Pet pet){
        petRepository.save(pet);
    }
}
