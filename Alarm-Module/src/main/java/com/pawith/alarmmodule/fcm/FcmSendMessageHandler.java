package com.pawith.alarmmodule.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.pawith.alarmmodule.exception.AlarmError;
import com.pawith.alarmmodule.exception.FcmException;
import com.pawith.alarmmodule.handler.NotificationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class FcmSendMessageHandler implements NotificationHandler<FcmSendMessageHandler.NotificationInfo> {
    private final FirebaseMessaging firebaseMessaging;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Retryable(
        retryFor = FcmException.class,
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000)
    )
    public void send(final NotificationInfo notificationInfo) {
        final Message message = Message.builder()
            .setToken(notificationInfo.deviceToken)
            .setNotification(notificationInfo.notification)
            .build();
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new FcmException(AlarmError.FCM_SEND_ERROR);
        }
    }

    public void sendAsync(final NotificationInfo notificationInfo){
        executorService.submit(() -> {
            send(notificationInfo);
        });
    }

    public void sendMultiAsync(Collection<NotificationInfo> notificationInfoList){
        notificationInfoList.forEach(this::sendAsync);
    }

    public record NotificationInfo(String deviceToken, Notification notification){ }
}
