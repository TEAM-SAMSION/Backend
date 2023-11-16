package com.pawith.todoapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoTeamInfoChangeRequest {
    private String teamName;
    private String description;
}
