package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.todoapplication.dto.response.TodoTeamSimpleResponse;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoTeamQueryService;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoTeamGetUseCase {
    private final RegisterQueryService registerQueryService;
    private final TodoTeamQueryService todoTeamQueryService;

    public SliceResponse<TodoTeamSimpleResponse> getTodoTeams(final Pageable pageable){
        final User requestUser = UserUtils.getAccessUser();
        final Slice<Register> registers = registerQueryService.findRegisterSliceByUserId(requestUser.getId(), pageable);
        Slice<TodoTeamSimpleResponse> todoTeamSimpleResponseSlice = registers.map(register -> {
            final TodoTeam todoTeam = todoTeamQueryService.findTodoTeamById(register.getTodoTeam().getId());
            final LocalDate registerDate = register.getCreatedAt().toLocalDate();
            final int registerPeriod = Period.between(registerDate, LocalDate.now()).getDays();
            return new TodoTeamSimpleResponse(todoTeam.getId(), todoTeam.getTeamName(), register.getAuthority(), registerPeriod);
        });
        return SliceResponse.from(todoTeamSimpleResponseSlice);
    }
}
