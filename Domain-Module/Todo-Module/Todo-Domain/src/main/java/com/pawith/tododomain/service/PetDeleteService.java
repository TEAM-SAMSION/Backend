package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class PetDeleteService {

    private final PetRepository petRepository;

    public void deletePet(Long petId) {
        petRepository.deleteById(petId);
    }
}
