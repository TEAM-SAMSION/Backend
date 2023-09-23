package com.pawith.todoapplication.mapper;

import com.pawith.todoapplication.dto.request.TodoTeamCreateRequest;
import com.pawith.todoapplication.dto.response.TodoTeamSimpleResponse;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoTeamMapper {

    public static TodoTeam mapToTodoTeam(final TodoTeamCreateRequest request) {
        return TodoTeam.builder()
            .teamCode(request.getRandomCode())
            .teamName(request.getTeamName())
            .imageUrl(null)
            .build();
    }

    public static TodoTeamSimpleResponse mapToTodoTeamSimpleResponse(final TodoTeam todoTeam, final Register register) {
        final int registerPeriod = Period.between(register.getCreatedAt().toLocalDate(), LocalDate.now()).getDays();
        return TodoTeamSimpleResponse.builder()
            .teamId(todoTeam.getId())
            .teamName(todoTeam.getTeamName())
            .teamProfileImageUrl(todoTeam.getImageUrl())
            .authority(register.getAuthority())
            .registerPeriod(registerPeriod)
            .build();
    }
}
