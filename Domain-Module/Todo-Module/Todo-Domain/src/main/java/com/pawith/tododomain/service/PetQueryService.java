package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Pet;
import com.pawith.tododomain.repository.PetRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
@Transactional
public class PetQueryService {
    private final PetRepository petRepository;

    public Integer countPetByTodoTeamId(Long todoTeamId){
        return petRepository.countByTodoTeamId(todoTeamId);
    }
    public List<Pet> findAllByTodoTeamId(Long todoTeamId){
        return petRepository.findAllByTodoTeamId(todoTeamId);
    }
    public Pet findPetById(Long petId) {
        return petRepository.findById(petId).orElseThrow();
    }
}
