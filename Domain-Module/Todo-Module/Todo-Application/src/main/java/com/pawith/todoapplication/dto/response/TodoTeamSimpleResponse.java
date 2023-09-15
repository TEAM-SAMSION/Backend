package com.pawith.todoapplication.dto.response;

import com.pawith.tododomain.entity.Authority;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoTeamSimpleResponse {
    private Long teamId;
    private String teamName;
    private Authority authority;
    private Integer registerPeriod;
}
