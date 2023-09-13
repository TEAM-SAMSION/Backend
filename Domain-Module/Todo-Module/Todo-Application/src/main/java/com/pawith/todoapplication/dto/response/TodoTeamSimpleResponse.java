package com.pawith.todoapplication.dto.response;

import com.pawith.tododomain.entity.Authority;
import lombok.Getter;

@Getter
public class TodoTeamSimpleResponse {
    private final Long teamId;
    private final String teamName;
    private final Authority authority;
    private final Integer registerPeriod;

    public TodoTeamSimpleResponse(Long teamId, String teamName, Authority authority, Integer registerPeriod) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.authority = authority;
        this.registerPeriod = registerPeriod;
    }
}
