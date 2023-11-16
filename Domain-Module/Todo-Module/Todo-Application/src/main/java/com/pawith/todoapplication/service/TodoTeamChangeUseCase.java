package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.imagemodule.service.ImageUploadService;
import com.pawith.todoapplication.dto.request.TodoTeamInfoChangeRequest;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.service.PetQueryService;
import com.pawith.tododomain.service.TodoTeamQueryService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class TodoTeamChangeUseCase {

    private final ImageUploadService imageUploadService;
    private final TodoTeamQueryService todoTeamQueryService;

    public void updateTodoTeam(Long todoTeamId, MultipartFile teamImageFile, TodoTeamInfoChangeRequest request) {
        CompletableFuture<String> teamImageAsync = imageUploadService.uploadImgAsync(teamImageFile);
        TodoTeam todoTeam = todoTeamQueryService.findTodoTeamById(todoTeamId);
        todoTeam.updateTodoTeam(request.getTeamName(), request.getDescription(), teamImageAsync.join());
    }

}
