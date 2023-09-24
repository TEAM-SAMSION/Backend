package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.exception.TodoNotFoundException;
import com.pawith.tododomain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoQueryService {

    private final TodoRepository todoRepository;

    public Todo findTodoByTodoId(Long todoId) {
        return todoRepository.findById(todoId)
            .orElseThrow(() -> new TodoNotFoundException(Error.TODO_NOT_FOUND));
    }

    public Integer findTodoCompleteRate(Long userId, Long todoTeamId) {
        final LocalDate now = LocalDate.now();
        final Long countTodayTodo = todoRepository.countTodoByDate(userId, todoTeamId, now);
        final Long countCompleteTodayTodo = todoRepository.countCompleteTodoByDate(userId, todoTeamId, now);
        return (int) ((countCompleteTodayTodo / (double) countTodayTodo) * 100);
    }

    public Slice<Todo> findTodayTodoSlice(Long userId, Long todoTeamId, Pageable pageable) {
        final LocalDate now = LocalDate.now();
        return todoRepository.findTodoByDate(userId, todoTeamId, now, pageable);
    }
}
