package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@DomainService
@RequiredArgsConstructor
@Transactional
public class TodoQueryService {

    private final TodoRepository todoRepository;

    public Long countTodoByCategoryIdAndCreatedAt(Long categoryId, LocalDateTime createdAt) {
        return todoRepository.countByCategoryIdAndCreatedAt(categoryId, createdAt);
    }

    public Long countTodoByCategoryIdAndCreatedAtAndStatus(Long categoryId, LocalDateTime createdAt, String status) {
        return todoRepository.countByCategoryIdAndCreatedAtAndTodoStatus(categoryId, createdAt, status);
    }
}
