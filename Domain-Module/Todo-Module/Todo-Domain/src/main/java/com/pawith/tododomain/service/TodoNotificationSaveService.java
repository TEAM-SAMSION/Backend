package com.pawith.tododomain.service;


import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.TodoNotification;
import com.pawith.tododomain.repository.TodoNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@DomainService
@RequiredArgsConstructor
public class TodoNotificationSaveService {
    private final TodoNotificationRepository todoNotificationRepository;

    public void saveNotification(LocalTime notificationTime, Assign assign){
        todoNotificationRepository.findByAssignId(assign.getId())
                .ifPresentOrElse(
                        todoNotification -> todoNotification.updateNotificationTime(notificationTime),
                        () -> todoNotificationRepository.save(new TodoNotification(notificationTime, assign))
                );
    }
}
