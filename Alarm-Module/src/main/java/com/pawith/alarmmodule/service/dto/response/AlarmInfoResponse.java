package com.pawith.alarmmodule.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class AlarmInfoResponse {
    private final Long alarmId;
    private final String title;
    private final String message;
    private final Boolean isRead;
    private final LocalDateTime createdAt;
}
