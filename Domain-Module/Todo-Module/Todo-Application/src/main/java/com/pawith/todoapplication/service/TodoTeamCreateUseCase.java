package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.request.TodoTeamCreateRequest;
import com.pawith.todoapplication.mapper.PetMapper;
import com.pawith.todoapplication.mapper.TodoTeamMapper;
import com.pawith.tododomain.entity.Pet;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.service.PetSaveService;
import com.pawith.tododomain.service.RegisterSaveService;
import com.pawith.tododomain.service.TodoTeamSaveService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
public class TodoTeamCreateUseCase {

    private final UserUtils userUtils;
    private final TodoTeamSaveService todoTeamSaveService;
    private final RegisterSaveService registerSaveService;
    private final PetSaveService petSaveService;

    @Transactional
    public void createTodoTeam(TodoTeamCreateRequest request) {
        final TodoTeam todoTeam = TodoTeamMapper.mapToTodoTeam(request);
        todoTeamSaveService.saveTodoTeamEntity(todoTeam);
        final User user = userUtils.getAccessUser();
        registerSaveService.saveRegisterAboutPresident(todoTeam, user.getId());
        request.getPetRegisters().forEach(petRegister -> {
            final Pet pet = PetMapper.mapToPet(petRegister, todoTeam, petRegister.getImageUrl());
            petSaveService.savePetEntity(pet);
        });
    }

}
