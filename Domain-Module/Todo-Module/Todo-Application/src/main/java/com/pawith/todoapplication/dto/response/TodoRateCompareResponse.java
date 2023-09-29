package com.pawith.todoapplication.dto.response;

import lombok.Getter;

@Getter
public class TodoRateCompareResponse {
    Boolean isHigherThanLastWeek;

    public TodoRateCompareResponse(Boolean isHigherThanLastWeek) {
        this.isHigherThanLastWeek = isHigherThanLastWeek;
    }
}
