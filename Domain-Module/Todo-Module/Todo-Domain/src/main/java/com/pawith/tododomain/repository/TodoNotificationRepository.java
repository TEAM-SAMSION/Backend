package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.TodoNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoNotificationRepository extends JpaRepository<TodoNotification, Long> {

}
