package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.imagedomain.service.ImageUploadService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CompletableFuture;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class TodoTeamCreateUseCase {

    private final UserUtils userUtils;
    private final TodoTeamSaveService todoTeamSaveService;
    private final RegisterSaveService registerSaveService;
    private final PetSaveService petSaveService;
    private final ImageUploadService imageUploadService;

    public void createTodoTeam(List<MultipartFile> imageFiles, TodoTeamCreateRequest request) {
        List<CompletableFuture<String>> imageAsync = imageUploadService.uploadImgListAsync(imageFiles);
        final TodoTeam todoTeam = TodoTeamMapper.mapToTodoTeam(request);
        todoTeamSaveService.saveTodoTeamEntity(todoTeam);
        final User user = userUtils.getAccessUser();
        registerSaveService.saveRegisterAboutPresident(todoTeam, user.getId());
        ListIterator<CompletableFuture<String>> futureListIterator = imageAsync.listIterator();
        request.getPetRegisters().forEach(petRegister -> {
            String imageUrl = futureListIterator.next().join();
            final Pet pet = PetMapper.mapToPet(petRegister, todoTeam, imageUrl);
            petSaveService.savePetEntity(pet);
        });
    }

}
