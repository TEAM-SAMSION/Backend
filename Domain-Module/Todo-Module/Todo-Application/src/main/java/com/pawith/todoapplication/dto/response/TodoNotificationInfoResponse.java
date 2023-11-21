package com.pawith.todoapplication.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class TodoNotificationInfoResponse {
    private Boolean isNotification;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalTime notificationTime;
}
