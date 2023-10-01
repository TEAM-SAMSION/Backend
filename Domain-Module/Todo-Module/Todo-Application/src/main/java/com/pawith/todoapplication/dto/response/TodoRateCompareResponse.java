package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRateCompareResponse {
    private Boolean isHigherThanLastWeek;
    private Boolean isSameAsLastWeek;

}
