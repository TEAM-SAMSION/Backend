package com.pawith.tododomain.service;


import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.TodoNotification;
import com.pawith.tododomain.repository.TodoNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class TodoNotificationSaveService {
    private final TodoNotificationRepository todoNotificationRepository;

    public void saveNotificationEntity(TodoNotification notification){
        todoNotificationRepository.save(notification);
    }
}
