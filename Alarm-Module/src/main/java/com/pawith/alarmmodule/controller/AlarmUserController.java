package com.pawith.alarmmodule.controller;

import com.pawith.alarmmodule.service.AlarmUserService;
import com.pawith.alarmmodule.service.dto.request.DeviceTokenSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmUserController {
    private final AlarmUserService alarmUserService;

    @PostMapping("/alarms/token")
    public void postDeviceToken(@RequestBody DeviceTokenSaveRequest request){
        alarmUserService.saveDeviceToken(request);
    }
}
