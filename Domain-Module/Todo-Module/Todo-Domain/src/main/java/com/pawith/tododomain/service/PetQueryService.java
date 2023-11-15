package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.repository.PetRepository;
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

}
