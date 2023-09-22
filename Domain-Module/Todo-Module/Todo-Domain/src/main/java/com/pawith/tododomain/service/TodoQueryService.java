package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.exception.TodoNotFoundException;
import com.pawith.tododomain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DomainService
@RequiredArgsConstructor
@Transactional
public class TodoQueryService {

    private final TodoRepository todoRepository;

    public Todo findTodoByTodoId(Long todoId) {
        return todoRepository.findById(todoId)
            .orElseThrow(() -> new TodoNotFoundException(Error.TODO_NOT_FOUND));
    }

    public Integer findTodoCompleteRate(Long userId, Long todoTeamId) {
        final Long countTodayTodo = todoRepository.countTodayTodo(userId, todoTeamId);
        final Long countCompleteTodayTodo = todoRepository.countTodayCompleteTodo(userId, todoTeamId);
        return (int) ((countCompleteTodayTodo / (double) countTodayTodo) * 100);
    }

    public Slice<Todo> findTodayTodoSlice(Long userId, Long todoTeamId, Pageable pageable) {
        return todoRepository.findTodayTodo(userId, todoTeamId, pageable);
    }
}
