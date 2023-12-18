package com.pawith.commonmodule.event;

import com.google.firebase.messaging.Notification;

import java.util.List;

public record MultiNotificationEvent(List<NotificationEvent> notificationEvents) {
    public List<Notification> convertNotificationList(){
        return notificationEvents.stream()
            .map(NotificationEvent::toNotification)
            .toList();
    }

    public List<Long> extractAllUserIds(){
        return notificationEvents.stream()
            .map(NotificationEvent::getUserId)
            .toList();
    }
}