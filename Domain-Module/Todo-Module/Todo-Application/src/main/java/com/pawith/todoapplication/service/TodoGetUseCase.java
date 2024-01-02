package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.todoapplication.dto.response.AssignUserInfoResponse;
import com.pawith.todoapplication.dto.response.CategorySubTodoResponse;
import com.pawith.todoapplication.dto.response.TodoInfoResponse;
import com.pawith.todoapplication.dto.response.TodoNotificationInfoResponse;
import com.pawith.todoapplication.mapper.TodoMapper;
import com.pawith.tododomain.entity.*;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.TodoNotificationQueryService;
import com.pawith.tododomain.util.AssignUtils;
import com.pawith.tododomain.util.RegisterUtils;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoGetUseCase {

    private final UserUtils userUtils;
    private final UserQueryService userQueryService;
    private final AssignQueryService assignQueryService;
    private final TodoNotificationQueryService todoNotificationQueryService;


    public ListResponse<TodoInfoResponse> getTodoListByTodoTeamId(final Long todoTeamId) {
        final User user = userUtils.getAccessUser();
        final List<Assign> allAssigns = assignQueryService.findAllByUserIdAndTodoTeamIdAndScheduledDate(user.getId(), todoTeamId);
        List<TodoInfoResponse> todoInfoResponses = allAssigns.stream()
            .map(assign -> {
                final Todo todo = assign.getTodo();
                final Category category = todo.getCategory();
                return new TodoInfoResponse(todo.getId(), category.getId(), category.getName(), todo.getDescription(), assign.getCompletionStatus());
            })
            .sorted(Comparator.comparing(TodoInfoResponse::getCategoryId).reversed())
            .toList();
        return ListResponse.from(todoInfoResponses);
    }

    public ListResponse<CategorySubTodoResponse> getTodoListByCategoryId(Long categoryId, LocalDate moveDate) {
        final User accessUser = userUtils.getAccessUser();

        final List<Assign> assignList = assignQueryService.findAllAssignByCategoryIdAndScheduledDate(categoryId, moveDate);

        final Map<Todo, List<Assign>> todoAssignMap = AssignUtils.convertToTodoAssignMap(assignList);
        final List<Todo> todoList = List.copyOf(todoAssignMap.keySet());

        final Map<Long, TodoNotification> todoNotificationMap =
            todoNotificationQueryService.findMapTodoIdKeyAndTodoNotificationValueByTodoIdsAndUserId(todoList, accessUser.getId());

        final Map<Long, User> userMap = userQueryService.findMapWithUserIdKeyByIds(extractUserIds(assignList));

        final List<CategorySubTodoResponse> subTodoResponseList = todoList.stream()
            .map(todo -> {
                final AtomicReference<Boolean> isAssigned = new AtomicReference<>(false);
                final List<AssignUserInfoResponse> assignUserInfoResponses =
                    getAssignUserInfoResponses(todoAssignMap.get(todo), userMap, accessUser.getId(), isAssigned);
                final TodoNotificationInfoResponse todoNotificationInfoResponse =
                    TodoNotificationInfoResponse.of(() -> Optional.ofNullable(todoNotificationMap.get(todo.getId())));
                return TodoMapper.mapToCategorySubTodoResponse(todo, assignUserInfoResponses, isAssigned.get(), todoNotificationInfoResponse);
            }).toList();
        return ListResponse.from(subTodoResponseList);
    }

    private List<AssignUserInfoResponse> getAssignUserInfoResponses(List<Assign> assigns, Map<Long, User> userMap, Long accessUserId, AtomicReference<Boolean> isAssigned) {
        return assigns.stream()
            .map(assign -> {
                final Register register = assign.getRegister();
                final User findUser = userMap.get(register.getUserId());
                if (Objects.equals(findUser.getId(), accessUserId)) {
                    isAssigned.set(true);
                }
                return new AssignUserInfoResponse(findUser.getId(), findUser.getNickname(), assign.getCompletionStatus());
            })
            .sorted(Comparator.comparing(assignUserInfoResponse -> !Objects.equals(assignUserInfoResponse.getAssigneeId(), accessUserId)))
            .toList();
    }

    private List<Long> extractUserIds(List<Assign> assigns) {
        return RegisterUtils.extractUserIds(assigns.stream().map(Assign::getRegister).toList());
    }

}
