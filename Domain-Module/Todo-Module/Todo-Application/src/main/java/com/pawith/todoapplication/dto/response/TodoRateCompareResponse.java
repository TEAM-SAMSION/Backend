package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRateCompareResponse {
    private ComparisonResult compareWithLastWeek;

    public enum ComparisonResult {
        HIGHER,
        SAME,
        LOWER
    }

    public TodoRateCompareResponse(Boolean isHigher, Boolean isSame) {
        if (isHigher) {
            compareWithLastWeek = ComparisonResult.HIGHER;
        } else if (isSame) {
            compareWithLastWeek = ComparisonResult.SAME;
        } else {
            compareWithLastWeek = ComparisonResult.LOWER;
        }
    }
}
