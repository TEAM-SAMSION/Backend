package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.exception.TodoNotFoundException;
import com.pawith.tododomain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class TodoQueryService {

    private final TodoRepository todoRepository;

    public Todo findTodoByTodoId(Long todoId) {
        return todoRepository.findById(todoId)
            .orElseThrow(() -> new TodoNotFoundException(TodoError.TODO_NOT_FOUND));
    }

    public Integer findTodoCompleteRate(Long userId, Long todoTeamId) {
        final LocalDate now = LocalDate.now();
        final Long countTodayTodo = todoRepository.countTodoByDateQuery(userId, todoTeamId, now);
        final Long countCompleteTodayTodo = todoRepository.countCompleteTodoByDateQuery(userId, todoTeamId, now);
        return (int) ((countCompleteTodayTodo / (double) countTodayTodo) * 100);
    }

    public List<Todo> findTodoListByCategoryIdAndScheduledDate(Long categoryId, LocalDate moveDate){
        return todoRepository.findTodoListByCategoryIdAndScheduledDateQuery(categoryId, moveDate);
    }

    public Integer findThisWeekTodoCompleteRate(Long userId, Long todoTeamId) {
        final LocalDate now = LocalDate.now();
        final LocalDate firstDayOfWeek = now.with(DayOfWeek.SUNDAY);
        return getWeekProgress(userId, todoTeamId, now, firstDayOfWeek);
    }

    public Integer findLastWeekTodoCompleteRate(Long userId, Long todoTeamId) {
        final LocalDate now = LocalDate.now();
        final LocalDate firstDayOfLastWeek = now.with(DayOfWeek.SUNDAY).minusWeeks(1);
        return getWeekProgress(userId, todoTeamId, now, firstDayOfLastWeek);
    }

    public Integer getWeekProgress(Long userId, Long todoTeamId, LocalDate now, LocalDate firstDayOfWeek) {
        final Long countWeekTodo = todoRepository.countTodoByBetweenDateQuery(userId, todoTeamId, now, firstDayOfWeek);
        final Long countCompleteWeekTodo = todoRepository.countCompleteTodoByBetweenDateQuery(userId, todoTeamId, now, firstDayOfWeek);
        return (int) ((countCompleteWeekTodo / (double) countWeekTodo) * 100);
    }

    public Slice<Todo> findAllTodoListByTodoTeamId(Long userId, Long todoTeamId, Pageable pageable) {
        return todoRepository.findTodoSliceByUserIdAndTodoTeamIdQuery(userId, todoTeamId, pageable);
    }

    public Slice<Todo> findAllTodoListByUserId(Long userId, Pageable pageable) {
        return todoRepository.findTodoSliceByUserIdQuery(userId, pageable);
    }

    public Integer countTodoByTodoTeamId(Long userId, Long todoTeamId) {
        return todoRepository.countTodoByUserIdAndTodoTeamIdQuery(userId, todoTeamId);
    }

    public Integer countTodoByUserId(Long userId) {
        return todoRepository.countTodoByUserIdQuery(userId);
    }
}
