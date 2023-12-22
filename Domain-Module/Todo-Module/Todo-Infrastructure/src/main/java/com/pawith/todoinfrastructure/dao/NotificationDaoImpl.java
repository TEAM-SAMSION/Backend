package com.pawith.todoinfrastructure.dao;

import com.pawith.tododomain.repository.dao.NotificationDao;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;
import java.time.LocalTime;

public record NotificationDaoImpl(
    Long todoTeamId, Long userId, String categoryName, String todoDescription,
    LocalTime notificationTime, LocalDate scheduledDate,String todoTeamName ) implements NotificationDao {

    @QueryProjection
    public NotificationDaoImpl {
    }

    @Override
    public Long getTodoTeamId() {
        return this.todoTeamId;
    }

    @Override
    public Long getUserId() {
        return this.userId;
    }

    @Override
    public String getCategoryName() {
        return this.categoryName;
    }

    @Override
    public String getTodoDescription() {
        return this.todoDescription;
    }

    @Override
    public LocalTime getNotificationTime() {
        return this.notificationTime;
    }

    @Override
    public LocalDate getScheduledDate() {
        return this.scheduledDate;
    }

    @Override
    public String getTodoTeamName() {
        return this.todoTeamName;
    }
}
