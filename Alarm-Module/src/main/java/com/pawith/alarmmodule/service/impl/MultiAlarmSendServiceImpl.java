package com.pawith.alarmmodule.service.impl;

import com.google.firebase.messaging.Notification;
import com.pawith.alarmmodule.entity.Alarm;
import com.pawith.alarmmodule.entity.AlarmUser;
import com.pawith.alarmmodule.entity.vo.AlarmBody;
import com.pawith.alarmmodule.fcm.FcmSendMessageHandler;
import com.pawith.alarmmodule.handler.NotificationHandler;
import com.pawith.alarmmodule.service.AlarmService;
import com.pawith.alarmmodule.service.AlarmUserService;
import com.pawith.alarmmodule.service.MultiAlarmSendService;
import com.pawith.commonmodule.event.MultiNotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MultiAlarmSendServiceImpl implements MultiAlarmSendService<MultiNotificationEvent> {
    private final AlarmUserService alarmUserService;
    private final AlarmService alarmService;
    private final NotificationHandler<FcmSendMessageHandler.NotificationInfo> notificationHandler;

    @Override
    @EventListener
    public void sendAlarm(MultiNotificationEvent multiNotificationEvent) {
        final Map<Long, AlarmUser> alarmUserMap = alarmUserService.findAlarmUsersByUserIds(multiNotificationEvent.extractAllUserIds());
        saveAllAlarm(multiNotificationEvent, alarmUserMap);
        sendMultiNotification(multiNotificationEvent, alarmUserMap);
    }

    private void sendMultiNotification(MultiNotificationEvent multiNotificationEvent, Map<Long, AlarmUser> alarmUserMap) {
        final List<FcmSendMessageHandler.NotificationInfo> notificationInfoList = multiNotificationEvent.notificationEvents().stream()
            .map(notificationEvent -> {
                final String deviceToken = alarmUserMap.get(notificationEvent.getUserId()).getDeviceToken();
                final Notification notification = notificationEvent.toNotification();
                return new FcmSendMessageHandler.NotificationInfo(deviceToken, notification);
            }).toList();
        notificationHandler.sendMultiAsync(notificationInfoList);
    }

    private void saveAllAlarm(MultiNotificationEvent multiNotificationEvent, Map<Long, AlarmUser> alarmUserMap) {
        final List<Alarm> savedAlarmList = multiNotificationEvent.notificationEvents().stream()
            .map(notificationEvent -> Alarm.builder()
                .alarmCategory(notificationEvent.getTitle())
                .alarmUser(alarmUserMap.get(notificationEvent.getUserId()))
                .alarmBody(new AlarmBody(notificationEvent.getBody(), notificationEvent.getDomainId()))
                .build())
            .toList();
        alarmService.saveAllAlarm(savedAlarmList);
    }
}