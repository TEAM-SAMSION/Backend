package com.pawith.todoapplication.dto.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoTeamNameSimpleResponse {
    private Long teamId;
    private String teamName;
}
