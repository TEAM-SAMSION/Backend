package com.pawith.alarmmodule.service.dto.response;

import com.pawith.alarmmodule.entity.AlarmCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class AlarmInfoResponse {
    private final AlarmCategory title;
    private final String body;
    private final Boolean isRead;
    private final LocalDateTime createdAt;
}
