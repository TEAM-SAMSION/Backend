package com.pawith.todoapplication.dto.response;

import com.pawith.tododomain.entity.Authority;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoTeamInfoResponse {
    private Long teamId;
    private String teamProfileImageUrl;
    private String teamName;
    private Authority authority;
    private Integer registerPeriod;
}
