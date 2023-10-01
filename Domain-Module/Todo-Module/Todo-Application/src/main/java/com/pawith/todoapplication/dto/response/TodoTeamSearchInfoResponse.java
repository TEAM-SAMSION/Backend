package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TodoTeamSearchInfoResponse {
    private final String code;
    private final String teamName;
    private final String presidentName;
    private final Integer registerCount;
    private final String description;
    private final String teamImageUrl;
}
