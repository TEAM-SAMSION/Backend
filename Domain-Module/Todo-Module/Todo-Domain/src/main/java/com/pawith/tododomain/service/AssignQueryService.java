package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.repository.AssignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AssignQueryService {

    private final AssignRepository assignRepository;

    public List<Assign> findTodayAssignByRegisterId(Long registerId) {
        LocalDate serverTime = getServerTime();
        return assignRepository.findAllByRegisterIdAndCreatedAtBetween(registerId, serverTime.atStartOfDay(), serverTime.atTime(LocalTime.MAX));
    }

    public Slice<Assign> findTodayAssignSliceByRegisterId(Long registerId, Pageable pageable) {
        LocalDate serverTime = getServerTime();
        return assignRepository.findAllByRegisterIdAndCreatedAtBetween(registerId, serverTime.atStartOfDay(), serverTime.atTime(LocalTime.MAX), pageable);
    }

    public LocalDate getServerTime() {
        return LocalDate.now();
    }
}
