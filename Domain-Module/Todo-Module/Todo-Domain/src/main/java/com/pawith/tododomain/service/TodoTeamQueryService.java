package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.exception.TodoTeamNotFoundException;
import com.pawith.tododomain.repository.TodoTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoTeamQueryService {

    private final TodoTeamRepository todoTeamRepository;

    public List<TodoTeam> findAllTodoTeamByUserId(Long userId){
        return todoTeamRepository.findAllByUserIdQuery(userId);
    }

    public TodoTeam findTodoTeamById(Long todoTeamId) {
        return findTodo(todoTeamRepository::findById, todoTeamId);
    }

    public TodoTeam findTodoTeamByCode(String todoTeamCode) {
        return findTodo(todoTeamRepository::findByTeamCode, todoTeamCode);
    }

    public TodoTeam findTodoTeamByTodoId(Long todoId) {
        return todoTeamRepository.findByTodoId(todoId);
    }

     private <T> TodoTeam findTodo(Function<T, Optional<TodoTeam>> findMethod, T specificationData){
        return findMethod.apply(specificationData)
            .orElseThrow(() -> new TodoTeamNotFoundException(TodoError.TODO_TEAM_NOT_FOUND));
     }
}
