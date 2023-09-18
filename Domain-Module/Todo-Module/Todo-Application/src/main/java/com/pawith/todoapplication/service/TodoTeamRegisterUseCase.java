package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.service.RegisterSaveService;
import com.pawith.tododomain.service.TodoTeamQueryService;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.utils.UserUtils;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class TodoTeamRegisterUseCase {
    private final TodoTeamQueryService todoTeamQueryService;
    private final RegisterSaveService registerSaveService;

    public void registerTodoTeam(String todoTeamCode) {
        final User user = UserUtils.getAccessUser();
        TodoTeam todoTeam = todoTeamQueryService.findTodoByCode(todoTeamCode);
        registerSaveService.saveRegisterAboutMember(todoTeam, user.getId());
    }
}
