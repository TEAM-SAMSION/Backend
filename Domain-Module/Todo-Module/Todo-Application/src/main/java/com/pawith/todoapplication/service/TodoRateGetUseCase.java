package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.response.TodoProgressResponse;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.service.CategoryQueryService;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.tododomain.service.TodoTeamQueryService;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoRateGetUseCase {

    private final TodoQueryService todoQueryService;
    private final TodoTeamQueryService todoTeamQueryService;
    private final RegisterQueryService registerQueryService;
    private final CategoryQueryService categoryQueryService;

    public TodoProgressResponse getTodoProgress(Long todoTeamId) {
        if(todoTeamId == null) {
            final User user = UserUtils.getAccessUser();
            final Register register = registerQueryService.findRecentRegisterByUserId(user.getId());
            todoTeamQueryService.findTodoTeamById(register.getTodoTeam().getId());
        }
        List<Category> categoryList = categoryQueryService.findCategoryListByTodoTeamId(todoTeamId);
        Long totalTodoCount = categoryList.stream()
                .mapToLong(category -> todoQueryService.countTodoByCategoryIdAndCreatedAt(category.getId(), LocalDateTime.now()))
                .sum();
        Long doneTodoCount = categoryList.stream()
                .mapToLong(category -> todoQueryService.countTodoByCategoryIdAndCreatedAtAndStatus(category.getId(), LocalDateTime.now(), "COMPLETE"))
                .sum();
        int result =  (int)((double)doneTodoCount/totalTodoCount*100);
        return new TodoProgressResponse(result);
    }
}
