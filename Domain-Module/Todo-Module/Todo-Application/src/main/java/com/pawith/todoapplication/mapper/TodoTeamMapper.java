package com.pawith.todoapplication.mapper;

import com.pawith.todoapplication.dto.request.TodoTeamCreateRequest;
import com.pawith.todoapplication.dto.response.TodoTeamRandomCodeResponse;
import com.pawith.todoapplication.dto.response.TodoTeamSearchInfoResponse;
import com.pawith.todoapplication.dto.response.TodoTeamInfoResponse;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.userdomain.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoTeamMapper {

    public static TodoTeam mapToTodoTeam(final TodoTeamCreateRequest request, String imageUrl) {
        return TodoTeam.builder()
            .teamCode(request.getRandomCode())
            .teamName(request.getTeamName())
            .imageUrl(imageUrl)
            .build();
    }

    public static TodoTeamInfoResponse mapToTodoTeamSimpleResponse(final TodoTeam todoTeam, final Register register) {
        final int registerPeriod = Period.between(register.getCreatedAt().toLocalDate(), LocalDate.now()).getDays();
        return TodoTeamInfoResponse.builder()
            .teamId(todoTeam.getId())
            .teamName(todoTeam.getTeamName())
            .teamProfileImageUrl(todoTeam.getImageUrl())
            .authority(register.getAuthority())
            .registerPeriod(registerPeriod)
            .build();
    }

    public static TodoTeamSearchInfoResponse mapToTodoTeamSearchInfoResponse(final TodoTeam todoTeam, final User user, final Integer registerCount) {
        return TodoTeamSearchInfoResponse.builder()
            .teamName(todoTeam.getTeamName())
            .code(todoTeam.getTeamCode())
            .description(null)
            .teamImageUrl(todoTeam.getImageUrl())
            .presidentName(user.getNickname())
            .registerCount(registerCount)
            .build();
    }

    public static TodoTeamRandomCodeResponse mapToTodoTeamRandomCodeResponse(final TodoTeam todoTeam) {
        return new TodoTeamRandomCodeResponse(todoTeam.getTeamCode());
    }
}
