package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.entity.TodoNotification;
import com.pawith.tododomain.repository.TodoNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoNotificationQueryService {

    private final TodoNotificationRepository todoNotificationRepository;

    public Map<Long, TodoNotification> findMapTodoIdKeyAndTodoNotificationValueByTodoIdsAndUserId(List<Todo> todoList, Long userId) {
        final List<Long> todoIds = todoList.stream().map(Todo::getId).collect(Collectors.toList());
        return todoNotificationRepository.findAllByTodoIdsAndUserIdWithInCompleteAssignQuery(todoIds, userId)
            .stream()
            .collect(Collectors.toMap(todoNotification -> {
                final Assign assign = todoNotification.getAssign();
                final Todo todo = assign.getTodo();
                return todo.getId();
            }, todoNotification -> todoNotification));
    }
}
