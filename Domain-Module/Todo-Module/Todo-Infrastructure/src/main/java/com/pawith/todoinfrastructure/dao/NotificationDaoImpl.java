package com.pawith.todoinfrastructure.dao;

import com.pawith.tododomain.repository.dao.NotificationDao;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalTime;

public record NotificationDaoImpl(Long todoTeamId, Long userId, String categoryName, String todoDescription,
                                  LocalTime notificationTime) implements NotificationDao {

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
}
