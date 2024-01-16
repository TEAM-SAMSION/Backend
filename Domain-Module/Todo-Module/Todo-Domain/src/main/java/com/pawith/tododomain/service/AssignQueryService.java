package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.CompletionStatus;
import com.pawith.tododomain.exception.AssignNotFoundException;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.repository.AssignRepository;
import com.pawith.tododomain.repository.dao.IncompleteAssignInfoDao;
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

    public Long countAssignByTodoIdAndCompleteStatus(Long todoId, CompletionStatus completionStatus) {
        return assignRepository.countByTodoIdAndCompletedQuery(todoId, completionStatus);
    }

    public Long countAssignByTodoId(Long todoId) {
        return assignRepository.countByTodoIdAndCompletedQuery(todoId, null);
    }

    public List<IncompleteAssignInfoDao> findAllIncompleteAssignInfoByTodoId(Long todoId) {
        return assignRepository.findAllAssignInfoByTodoIdAndCompleteStatusQuery(todoId, CompletionStatus.INCOMPLETE);
    }

}
