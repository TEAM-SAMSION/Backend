package com.pawith.commonmodule.event;

import java.util.List;

public record MultiNotificationEvent(List<NotificationEvent> notificationEvents) {
    public List<Long> extractAllUserIds(){
        return notificationEvents.stream()
            .map(NotificationEvent::userId)
            .toList();
    }
}