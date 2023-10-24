package com.pawith.todoapplication.dto.request;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduledDateChangeRequest {

    private LocalDate scheduledDate;

    public ScheduledDateChangeRequest(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}
