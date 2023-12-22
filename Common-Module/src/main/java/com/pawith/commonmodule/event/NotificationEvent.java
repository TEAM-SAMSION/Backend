package com.pawith.commonmodule.event;

import com.google.firebase.messaging.Notification;
import lombok.Builder;

public record NotificationEvent(Long userId, String title, String body, Long domainId) {
    @Builder
    public NotificationEvent {
    }

    public Notification toNotification(){
        return Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build();
    }
}
