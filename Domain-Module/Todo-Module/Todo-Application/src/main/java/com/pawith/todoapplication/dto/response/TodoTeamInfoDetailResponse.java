package com.pawith.todoapplication.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoTeamInfoDetailResponse {
    private String todoTeamCode;
    private String teamDescription;
    private Integer teamMemberCount;
    private Integer teamPetCount;
}
