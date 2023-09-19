package com.pawith.todoapplication.dto.response;

import com.pawith.tododomain.entity.Authority;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoTeamSimpleResponse {
    private Long teamId;
    private String teamProfileImageUrl;
    private String teamName;
    private Authority authority;
    private Integer registerPeriod;
}
