package com.pawith.todoapplication.handler;

import com.pawith.commonmodule.cache.CacheTemplate;
import com.pawith.commonmodule.event.MultiNotificationEvent;
import com.pawith.commonmodule.event.NotificationEvent;
import com.pawith.todoapplication.handler.event.TodoCompletionCheckEvent;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.service.AssignQueryService;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.tododomain.service.TodoTeamQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Transactional
public class TodoRemindHandler {
    private static final String NOTIFICATION_MESSAGE = "'%s'을 %d명이 완료했어요! %s님도 얼른 완료해볼까요?";
    private static final String REMIND_CACHE_KEY = "Remind:%s";

    private final AssignQueryService assignQueryService;
    private final UserQueryService userQueryService;
    private final TodoQueryService todoQueryService;
    private final TodoTeamQueryService todoTeamQueryService;
    private final CacheTemplate<String, String> cacheTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    @EventListener
    public void remindTodo(TodoCompletionCheckEvent todoCompletionCheckEvent){
        final List<Assign> allAssigns = assignQueryService.findAllAssignWithRegisterByTodoId(todoCompletionCheckEvent.todoId());
        final Long domainId = todoCompletionCheckEvent.todoId();
        final String cacheKey = String.format(REMIND_CACHE_KEY, domainId);
        if(isRemindable(allAssigns)&& !cacheTemplate.opsForSet().contains(cacheKey)){
            cacheTemplate.opsForSet().addWithExpireAfterToday(cacheKey);
            final List<NotificationEvent> todoNotificationList = buildNotificationEvent(allAssigns, domainId);
            applicationEventPublisher.publishEvent(new MultiNotificationEvent(todoNotificationList));
        }
    }

    private List<NotificationEvent> buildNotificationEvent(List<Assign> allAssigns, Long domainId) {
        final List<Long> incompleteTodoUserIds = allAssigns.stream()
            .filter(assign -> !assign.isCompleted())
            .map(assign -> assign.getRegister().getUserId())
            .toList();
        final long completeNumber = allAssigns.size()- incompleteTodoUserIds.size();
        final Map<Long, User> incompleteTodoUserMap = userQueryService.findMapWithUserIdKeyByIds(incompleteTodoUserIds);
        final String todoDescription = todoQueryService.findTodoByTodoId(domainId).getDescription();
        final String todoTeamName = todoTeamQueryService.findTodoTeamByTodoId(domainId).getTeamName();
        return incompleteTodoUserIds.stream()
            .map(userId -> {
                final User user = incompleteTodoUserMap.get(userId);
                final String message = String.format(NOTIFICATION_MESSAGE, todoDescription, completeNumber, user.getNickname());
                return new NotificationEvent(userId, todoTeamName, message, domainId);
            })
            .toList();
    }

    private boolean isRemindable(List<Assign> allAssigns){
        final long completeNumber = allAssigns.stream()
            .filter(Assign::isCompleted)
            .count();
        return (float)completeNumber >= (float) allAssigns.size() /2;
    }
}
