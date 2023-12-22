package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.TodoNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoNotificationRepository extends JpaRepository<TodoNotification, Long>, TodoNotificationQueryRepository {

    Optional<TodoNotification> findByAssignId(Long assignId);
}
