package com.pawith.todopresentation;

import com.pawith.commonmodule.response.ListResponse;
import com.pawith.todoapplication.dto.request.ScheduledDateChangeRequest;
import com.pawith.todoapplication.dto.request.TodoCreateRequest;
import com.pawith.todoapplication.dto.request.TodoDescriptionChangeRequest;
import com.pawith.todoapplication.dto.response.*;
import com.pawith.todoapplication.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TodoController {
    private final TodoGetUseCase todoGetUseCase;
    private final TodoRateGetUseCase todoRateGetUseCase;
    private final TodoCreateUseCase todoCreateUseCase;
    private final TodoChangeUseCase todoChangeUseCase;
    private final AssignChangeUseCase assignChangeUseCase;
    private final TodoDeleteUseCase todoDeleteUseCase;
    private final TodoNotificationCreateUseCase todoNotificationCreateUseCase;

    @GetMapping("/{todoTeamId}/todos/progress")
    public TodoProgressResponse getTodoProgress(@PathVariable Long todoTeamId) {
        return todoRateGetUseCase.getTodoProgress(todoTeamId);
    }

    @GetMapping("/{todoTeamId}/todos")
    public ListResponse<TodoInfoResponse> getTodos(@PathVariable Long todoTeamId) {
        return todoGetUseCase.getTodoListByTodoTeamId(todoTeamId);
    }

    @PostMapping("/todos")
    public void postTodo(@RequestBody TodoCreateRequest todoCreateRequest){
        todoCreateUseCase.createTodo(todoCreateRequest);
    }

    @GetMapping("/category/{categoryId}/todos")
    public ListResponse<CategorySubTodoResponse> getTodosAboutCategorySubTodo(@PathVariable Long categoryId, @RequestParam("moveDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate moveDate){
        return todoGetUseCase.getTodoListByCategoryId(categoryId, moveDate);
    }

    @GetMapping("/{todoTeamId}/todos/progress/compare")
    public TodoProgressRateCompareResponse getTodoWeekProgressCompare(@PathVariable Long todoTeamId){
        return todoRateGetUseCase.getWeekProgressCompare(todoTeamId);
    }


    @PutMapping("/todos/{todoId}/date")
    public void putTodoScheduledDate(@PathVariable Long todoId, @RequestBody ScheduledDateChangeRequest scheduledDateChangeRequest){
        todoChangeUseCase.changeScheduledDate(todoId, scheduledDateChangeRequest);
    }

    @PutMapping("/todos/{todoId}/description")
    public void putTodoDescription(@PathVariable Long todoId, @RequestBody TodoDescriptionChangeRequest todoDescriptionChangeRequest){
        todoChangeUseCase.changeTodoName(todoId, todoDescriptionChangeRequest);
    }

    @PutMapping("/todos/{todoId}/assign/complete")
    public void putAssignStatus(@PathVariable Long todoId){
        assignChangeUseCase.changeAssignStatus(todoId);
    }

    @GetMapping("/todos/{todoId}/completion")
    public TodoCompletionResponse getTodoCompletion(@PathVariable Long todoId){
        return todoGetUseCase.getTodoCompletion(todoId);
    }

    @DeleteMapping("/todos/{todoId}")
    public void deleteTodoById(@PathVariable Long todoId){
        todoDeleteUseCase.deleteTodoByTodoId(todoId);
    }

    @PostMapping("/todos/{todoId}/assign/notification")
    public void postTodoAssignAlarm(@PathVariable Long todoId, @RequestParam("notificationTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime notificationTime){
        todoNotificationCreateUseCase.createNotification(todoId, notificationTime);
    }

}
