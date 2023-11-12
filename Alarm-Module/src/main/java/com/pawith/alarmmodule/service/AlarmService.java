package com.pawith.alarmmodule.service;

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
            .map(alarm -> new AlarmInfoResponse(alarm.getAlarmCategory(), alarm.getBody(), alarm.getIsRead(), alarm.getCreatedAt()));
        return SliceResponse.from(alarmInfoResponses);
    }
}
