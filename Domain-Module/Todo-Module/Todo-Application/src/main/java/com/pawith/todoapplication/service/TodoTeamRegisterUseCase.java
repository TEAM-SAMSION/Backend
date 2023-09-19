package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.service.RegisterSaveService;
import com.pawith.tododomain.service.TodoTeamQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class TodoTeamRegisterUseCase {
    private final UserUtils userUtils;
    private final TodoTeamQueryService todoTeamQueryService;
    private final RegisterSaveService registerSaveService;

    public void registerTodoTeam(String todoTeamCode) {
        final User user = userUtils.getAccessUser();
        TodoTeam todoTeam = todoTeamQueryService.findTodoByCode(todoTeamCode);
        registerSaveService.saveRegisterAboutMember(todoTeam, user.getId());
    }
}
