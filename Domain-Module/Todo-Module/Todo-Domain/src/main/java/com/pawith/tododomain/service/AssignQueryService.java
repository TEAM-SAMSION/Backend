package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.repository.AssignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AssignQueryService {

    private final AssignRepository assignRepository;

    public Slice<Assign> findTodayAssignSliceByRegisterId(Long userId, Long todoTeamId, Pageable pageable) {
        return assignRepository.findTodayAssign(userId, todoTeamId, pageable);
    }

}
