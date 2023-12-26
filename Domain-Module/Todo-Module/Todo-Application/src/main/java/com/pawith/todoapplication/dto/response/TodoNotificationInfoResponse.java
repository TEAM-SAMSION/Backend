package com.pawith.todoapplication.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pawith.tododomain.entity.TodoNotification;

import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Supplier;

public record TodoNotificationInfoResponse(
    Boolean isNotification,
    @JsonInclude(JsonInclude.Include.NON_NULL) LocalTime notificationTime
) {
    public static TodoNotificationInfoResponse of(Supplier<Optional<TodoNotification>> todoNotificationSupplier) {
        return todoNotificationSupplier.get()
            .map(todoNotification -> new TodoNotificationInfoResponse(true, todoNotification.getNotificationTime()))
            .orElse(new TodoNotificationInfoResponse(false, null));
    }
}
