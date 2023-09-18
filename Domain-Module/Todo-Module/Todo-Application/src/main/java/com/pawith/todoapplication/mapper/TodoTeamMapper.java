package com.pawith.todoapplication.mapper;

import com.pawith.todoapplication.dto.request.TodoTeamCreateRequest;
import com.pawith.tododomain.entity.TodoTeam;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoTeamMapper {

    public static TodoTeam mapToTodoTeam(final TodoTeamCreateRequest request) {
        return TodoTeam.builder()
            .teamCode(request.getRandomCode())
            .teamName(request.getTeamName())
            .imageUrl(null)
            .build();
    }
}
