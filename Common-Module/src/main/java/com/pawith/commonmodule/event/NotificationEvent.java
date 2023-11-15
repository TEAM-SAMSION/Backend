package com.pawith.commonmodule.event;

import com.google.firebase.messaging.Notification;
import com.pawith.commonmodule.enums.AlarmCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NotificationEvent {
    private Long userId;
    private AlarmCategory title;
    private String body;
    private Long domainId;

    @Builder
    public NotificationEvent(Long userId, AlarmCategory title, String body, Long domainId) {
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.domainId = domainId;
    }

    public Notification toNotification(){
        return Notification.builder()
            .setTitle(title.name())
            .setBody(body)
            .build();
    }
}
