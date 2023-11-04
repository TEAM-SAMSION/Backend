package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.repository.AssignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AssignQueryService {

    private final AssignRepository assignRepository;

    public List<Assign> findAllAssignByCategoryIdAndScheduledDate(Long categoryId, LocalDate scheduledDate) {
        return assignRepository.findAllByCategoryIdAndScheduledDate(categoryId, scheduledDate);
    }

    public Assign findAssignByTodoIdAndUserId(Long todoId, Long userId) {
        return assignRepository.findByTodoIdAndUserId(todoId, userId);
    }

    public List<Assign> findAllAssignByTodoId(Long todoId) {
        return assignRepository.findAllByTodoId(todoId);
    }

}
