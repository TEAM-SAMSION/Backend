package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.exception.AssignNotFoundException;
import com.pawith.tododomain.exception.TodoError;
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
        return assignRepository.findAllByCategoryIdAndScheduledDateQuery(categoryId, scheduledDate);
    }

    public List<Assign> findAllByUserIdAndTodoTeamIdAndScheduledDate(Long userId, Long todoTeamId) {
        final LocalDate now = LocalDate.now();
        return assignRepository.findAllByUserIdAndTodoTeamIdAndScheduledDateQuery(userId, todoTeamId, now);
    }


    public Assign findAssignByTodoIdAndUserId(Long todoId, Long userId) {
        return assignRepository.findByTodoIdAndUserIdQuery(todoId, userId)
            .orElseThrow(() -> new AssignNotFoundException(TodoError.ASSIGN_NOT_FOUND));
    }

    public List<Assign> findAllAssignByTodoId(Long todoId) {
        return assignRepository.findAllByTodoIdQuery(todoId);
    }

    public List<Assign> findAllAssignWithRegisterByTodoId(Long todoId) {
        return assignRepository.findAllByTodoIdWithRegisterFetchQuery(todoId);
    }

}
