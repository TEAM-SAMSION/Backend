package com.pawith.alarmmodule.service.dto.request;

import com.pawith.alarmmodule.entity.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeviceTokenSaveRequest {
    private String deviceToken;
    private DeviceType deviceType;
}
