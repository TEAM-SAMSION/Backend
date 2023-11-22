package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.todoapplication.dto.response.*;
import com.pawith.todoapplication.mapper.TodoMapper;
import com.pawith.tododomain.entity.*;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.TodoNotificationQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoGetUseCase {

    private final UserUtils userUtils;
    private final TodoQueryService todoQueryService;
    private final UserQueryService userQueryService;
    private final RegisterQueryService registerQueryService;
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
            .collect(Collectors.toList());
        return ListResponse.from(todoInfoResponses);
    }

    public ListResponse<CategorySubTodoResponse> getTodoListByCategoryId(Long categoryId, LocalDate moveDate) {
        final User accessUser = userUtils.getAccessUser();
        final Map<Todo, List<Assign>> todoAssignMap = createTodoAssignListMap(categoryId, moveDate);
        final Map<Long, TodoNotification> todoNotificationMap = createTodoNotificationMapWithTodoId(new ArrayList<>(todoAssignMap.keySet()), accessUser.getId());
        final Map<Long, User> userMap = createUserIdMap(todoAssignMap);
        final ArrayList<CategorySubTodoResponse> todoMainResponses = new ArrayList<>();
        for (Todo todo : todoAssignMap.keySet()) {
            final AtomicReference<Boolean> isAssigned = new AtomicReference<>(false);
            final List<AssignUserInfoResponse> assignUserInfoResponses = todoAssignMap.get(todo).stream()
                .map(assign -> {
                    final Register register = assign.getRegister();
                    final User findUser = userMap.get(register.getUserId());
                    if (Objects.equals(findUser.getId(), accessUser.getId())) {
                        isAssigned.set(true);
                    }
                    return new AssignUserInfoResponse(findUser.getId(), findUser.getNickname(), assign.getCompletionStatus());
                })
                .sorted(Comparator.comparing(assignUserInfoResponse -> !Objects.equals(assignUserInfoResponse.getAssigneeId(), accessUser.getId())))
                .collect(Collectors.toList());
            TodoNotificationInfoResponse todoNotificationInfoResponse = getTodoNotificationInfoResponse(todo, todoNotificationMap);
            todoMainResponses.add(TodoMapper.mapToCategorySubTodoResponse(todo, assignUserInfoResponses, isAssigned.get(),todoNotificationInfoResponse));
        }
        return ListResponse.from(todoMainResponses);
    }

    private static TodoNotificationInfoResponse getTodoNotificationInfoResponse(Todo todo, Map<Long, TodoNotification> todoNotificationMap) {
        if(todoNotificationMap.containsKey(todo.getId())){
            final TodoNotification todoNotification = todoNotificationMap.get(todo.getId());
            final LocalTime notificationTime = todoNotification.getNotificationTime();
            return new TodoNotificationInfoResponse(true, notificationTime);
        }
        return new TodoNotificationInfoResponse(false, null);
    }

    private Map<Long, User> createUserIdMap(Map<Todo, List<Assign>> groupByTodo) {
        final List<Long> userIds = groupByTodo.values().stream()
            .flatMap(Collection::stream)
            .mapToLong(assign -> {
                final Register register = assign.getRegister();
                return register.getUserId();
            })
            .boxed()
            .collect(Collectors.toList());
        return userQueryService.findUserMapByIds(userIds);
    }

    private Map<Long, TodoNotification> createTodoNotificationMapWithTodoId(List<Todo> todoList, Long userId) {
        final List<Long> todoIds = todoList.stream().map(Todo::getId).collect(Collectors.toList());
        return todoNotificationQueryService.findAllByTodoIdsWithIncompleteAssign(todoIds, userId)
            .stream()
            .collect(Collectors.toMap(todoNotification -> todoNotification.getAssign().getTodo().getId(), todoNotification -> todoNotification));
    }

    public TodoCompletionResponse getTodoCompletion(Long todoId) {
        final Todo todo = todoQueryService.findTodoByTodoId(todoId);
        return new TodoCompletionResponse(todo.getCompletionStatus());
    }

    private Map<Todo, List<Assign>> createTodoAssignListMap(Long categoryId, LocalDate moveDate) {
        return assignQueryService.findAllAssignByCategoryIdAndScheduledDate(categoryId, moveDate)
            .stream()
            .collect(Collectors.groupingBy(Assign::getTodo, LinkedHashMap::new, Collectors.toList()));
    }

}
