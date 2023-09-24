package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.repository.AssignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class AssignSaveService {

    private final AssignRepository assignRepository;

    public void saveAssignEntity(Assign assign){
        assignRepository.save(assign);
    }
}
