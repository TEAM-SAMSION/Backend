package com.pawith.tododomain.repository.dao;

import java.time.LocalTime;

public interface NotificationDao {
    Long getTodoTeamId();
    Long getUserId();
    String getCategoryName();
    String getTodoDescription();
    LocalTime getNotificationTime();
}
