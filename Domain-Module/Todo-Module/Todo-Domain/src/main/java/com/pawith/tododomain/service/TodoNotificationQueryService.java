package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.TodoNotification;
import com.pawith.tododomain.repository.TodoNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoNotificationQueryService {

    private final TodoNotificationRepository todoNotificationRepository;

    public List<TodoNotification> findAllByTodoIdsWithIncompleteAssign(List<Long> todoIds, Long userId) {
        return todoNotificationRepository.findAllByTodoIdsAndUserIdWithInCompleteAssignQuery(todoIds, userId);
    }
}
