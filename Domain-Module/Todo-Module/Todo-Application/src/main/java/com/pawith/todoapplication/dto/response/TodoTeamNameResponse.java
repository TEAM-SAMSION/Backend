package com.pawith.todoapplication.dto.response;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoTeamNameResponse {
    private Long teamId;
    private String teamName;
}
