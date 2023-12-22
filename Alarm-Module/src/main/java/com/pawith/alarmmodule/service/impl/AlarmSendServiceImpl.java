package com.pawith.alarmmodule.service.impl;

import com.pawith.alarmmodule.entity.Alarm;
import com.pawith.alarmmodule.entity.AlarmUser;
import com.pawith.alarmmodule.entity.vo.AlarmBody;
import com.pawith.alarmmodule.fcm.FcmSendMessageHandler;
import com.pawith.alarmmodule.handler.NotificationHandler;
import com.pawith.alarmmodule.service.AlarmSendService;
import com.pawith.alarmmodule.service.AlarmService;
import com.pawith.alarmmodule.service.AlarmUserService;
import com.pawith.commonmodule.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmSendServiceImpl implements AlarmSendService<NotificationEvent> {
    private final AlarmUserService alarmUserService;
    private final AlarmService alarmService;
    private final NotificationHandler<FcmSendMessageHandler.NotificationInfo> notificationHandler;

    @Override
    @EventListener
    public void sendAlarm(NotificationEvent notificationEvent) {
        final AlarmUser alarmUser = alarmUserService.findDeviceTokenByUserId(notificationEvent.userId());
        alarmService.saveAlarm(Alarm.builder()
            .alarmCategory(notificationEvent.title())
            .alarmUser(alarmUser)
            .alarmBody(new AlarmBody(notificationEvent.body(), notificationEvent.domainId()))
            .build());
        notificationHandler.sendAsync(
            new FcmSendMessageHandler.NotificationInfo(
                alarmUser.getDeviceToken(),
                notificationEvent.toNotification())
        );
    }
}
