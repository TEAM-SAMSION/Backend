package com.pawith.alarmmodule.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.pawith.alarmmodule.entity.Alarm;
import com.pawith.alarmmodule.entity.AlarmUser;
import com.pawith.alarmmodule.entity.vo.AlarmBody;
import com.pawith.alarmmodule.exception.AlarmError;
import com.pawith.alarmmodule.exception.FcmException;
import com.pawith.alarmmodule.service.AlarmSendService;
import com.pawith.alarmmodule.service.AlarmService;
import com.pawith.alarmmodule.service.AlarmUserService;
import com.pawith.commonmodule.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmSendService implements AlarmSendService<NotificationEvent> {
    private final FirebaseMessaging firebaseMessaging;
    private final AlarmUserService alarmUserService;
    private final AlarmService alarmService;

    @Override
    @EventListener
    public void sendAlarm(NotificationEvent notificationEvent) {
        CompletableFuture.runAsync(() -> {
            final AlarmUser alarmUser = alarmUserService.findDeviceTokenByUserId(notificationEvent.getUserId());
            alarmService.saveAlarm(Alarm.builder()
                .alarmCategory(notificationEvent.getTitle())
                .alarmUser(alarmUser)
                .alarmBody(new AlarmBody(notificationEvent.getBody(), notificationEvent.getDomainId()))
                .build());
            sendFcmMessage(alarmUser.getDeviceToken(), notificationEvent.toNotification());
        });
    }

    @Retryable(
        value = {FcmException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000)
    )
    private void sendFcmMessage(final String deviceToken, final Notification notification) {
        final Message message = Message.builder()
            .setToken(deviceToken)
            .setNotification(notification)
            .build();
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new FcmException(AlarmError.FCM_SEND_ERROR);
        }
    }
}
