package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.repository.AssignBatchRepository;
import com.pawith.tododomain.repository.AssignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@DomainService
@RequiredArgsConstructor
public class AssignSaveService {

    private final AssignRepository assignRepository;
    private final AssignBatchRepository assignBatchRepository;

    public void saveAssignEntity(Assign assign){
        assignRepository.save(assign);
    }

    public void saveAllAssignEntity(Collection<Assign> assigns){
        assignBatchRepository.batchInsert(assigns);
    }
}
