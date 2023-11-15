package com.pawith.alarmmodule.service;

import com.pawith.alarmmodule.entity.Alarm;
import com.pawith.alarmmodule.repository.AlarmRepository;
import com.pawith.alarmmodule.service.dto.response.AlarmExistenceResponse;
import com.pawith.alarmmodule.service.dto.response.AlarmInfoResponse;
import com.pawith.commonmodule.response.SliceResponse;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserUtils userUtils;

    public AlarmExistenceResponse getAlarmsExist(){
        final Long userId = userUtils.getAccessUser().getId();
        final Boolean alarmExist = alarmRepository.existsByUserId(userId);
        return new AlarmExistenceResponse(alarmExist);
    }

    public SliceResponse<AlarmInfoResponse> getAlarms(Pageable pageable){
        final Long userId = userUtils.getAccessUser().getId();
        Slice<AlarmInfoResponse> alarmInfoResponses = alarmRepository.findAllByUserId(userId, pageable)
            .map(alarm -> AlarmInfoResponse.builder()
                .alarmId(alarm.getId())
                .title(alarm.getAlarmCategory())
                .message(alarm.getAlarmBody().getMessage())
                .isRead(alarm.getIsRead())
                .createdAt(alarm.getCreatedAt())
                .build());
        return SliceResponse.from(alarmInfoResponses);
    }

    public void saveAlarm(Alarm alarm){
        alarmRepository.save(alarm);
    }
}
