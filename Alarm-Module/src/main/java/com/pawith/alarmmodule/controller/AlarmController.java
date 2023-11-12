package com.pawith.alarmmodule.controller;

import com.pawith.alarmmodule.service.AlarmService;
import com.pawith.alarmmodule.service.dto.response.AlarmExistenceResponse;
import com.pawith.alarmmodule.service.dto.response.AlarmInfoResponse;
import com.pawith.commonmodule.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping("/alarms/exist")
    public AlarmExistenceResponse getAlarmsExist(){
        return alarmService.getAlarmsExist();
    }

    @GetMapping("/alarms")
    public SliceResponse<AlarmInfoResponse> getAlarms(Pageable pageable){
        return alarmService.getAlarms(pageable);
    }

}
