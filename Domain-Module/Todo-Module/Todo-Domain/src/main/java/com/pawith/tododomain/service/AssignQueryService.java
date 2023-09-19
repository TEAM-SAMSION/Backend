package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.repository.AssignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AssignQueryService {

    private final AssignRepository assignRepository;

    public List<Assign> findAssignByRegisterIdAndCreatedAtBetween(Long registerId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return assignRepository.findAllByRegisterIdAndCreatedAtBetween(registerId, startOfDay, endOfDay);
    }
}
