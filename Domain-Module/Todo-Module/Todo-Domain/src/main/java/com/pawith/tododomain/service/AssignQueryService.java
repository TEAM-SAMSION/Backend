package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.exception.AssignNotFoundException;
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

    public List<Assign> findAllByUserIdAndTodoTeamIdAndScheduledDate(Long userId, Long todoTeamId) {
        final LocalDate now = LocalDate.now();
        return assignRepository.findAllByUserIdAndTodoTeamIdAndScheduledDate(userId, todoTeamId, now);
    }


    public Assign findAssignByTodoIdAndUserId(Long todoId, Long userId) {
        return assignRepository.findByTodoIdAndUserId(todoId, userId)
            .orElseThrow(() -> new AssignNotFoundException(Error.ASSIGN_NOT_FOUND));
    }

    public List<Assign> findAllAssignByTodoId(Long todoId) {
        return assignRepository.findAllByTodoId(todoId);
    }

}
