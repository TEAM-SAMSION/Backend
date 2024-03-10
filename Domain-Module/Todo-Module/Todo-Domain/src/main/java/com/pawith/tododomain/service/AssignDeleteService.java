package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.repository.AssignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class AssignDeleteService {
    private final AssignRepository assignRepository;

    public void deleteAssignByRegisterId(final List<Long> registerId){
        assignRepository.deleteByRegisterIdsQuery(registerId);
    }

    public void deleteAllByTodoId(final Long todoId){
        assignRepository.deleteAllByTodoIdQuery(todoId);
    }

    public void deleteAssignByCategoryId(final Long categoryId){
        assignRepository.deleteAllByCategoryIdQuery(categoryId);
    }
}
