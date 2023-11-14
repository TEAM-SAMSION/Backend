package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.handler.event.CategoryDeleteEvent;
import com.pawith.tododomain.service.CategoryDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class CategoryDeleteUseCase {

    private final CategoryDeleteService categoryDeleteService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void deleteCategory(Long categoryId) {
        categoryDeleteService.deleteCategory(categoryId);
        applicationEventPublisher.publishEvent(new CategoryDeleteEvent(categoryId));
    }
}

