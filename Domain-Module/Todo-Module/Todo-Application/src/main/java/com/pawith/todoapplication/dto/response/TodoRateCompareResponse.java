package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRateCompareResponse {
    Boolean isHigherThanLastWeek;
    Boolean isSameAsLastWeek;

}
