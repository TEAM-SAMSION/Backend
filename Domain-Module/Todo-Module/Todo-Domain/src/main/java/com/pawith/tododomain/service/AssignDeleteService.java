package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.repository.AssignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@RequiredArgsConstructor
@Transactional
public class AssignDeleteService {
    private final AssignRepository assignRepository;

    public void deleteAssignByRegisterId(final List<Long> registerId){
        assignRepository.deleteByRegisterIds(registerId);
    }
}
