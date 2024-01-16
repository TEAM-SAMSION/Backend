package com.pawith.todoapplication.handler;

import com.pawith.commonmodule.cache.CacheTemplate;
import com.pawith.commonmodule.event.MultiNotificationEvent;
import com.pawith.commonmodule.event.NotificationEvent;
import com.pawith.todoapplication.handler.event.TodoCompletionCheckEvent;
import com.pawith.tododomain.entity.CompletionStatus;
import com.pawith.tododomain.repository.dao.IncompleteAssignInfoDao;
import com.pawith.tododomain.service.AssignQueryService;
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
    private final CacheTemplate<String, String> cacheTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    @EventListener
    public void remindTodo(final TodoCompletionCheckEvent todoCompletionCheckEvent){
        final Long todoId = todoCompletionCheckEvent.todoId();
        final String cacheKey = String.format(REMIND_CACHE_KEY, todoId);
        final long completeAssignNumber = assignQueryService.countAssignByTodoIdAndCompleteStatus(todoId, CompletionStatus.COMPLETE);
        if(isRemindable(todoId, completeAssignNumber)&& !cacheTemplate.opsForSet().contains(cacheKey)){
            cacheTemplate.opsForSet().addWithExpireAfterToday(cacheKey);
            final List<NotificationEvent> todoNotificationList = buildNotificationEvent(todoId, completeAssignNumber);
            applicationEventPublisher.publishEvent(new MultiNotificationEvent(todoNotificationList));
        }
    }

    private List<NotificationEvent> buildNotificationEvent(final Long todoId, final long completeAssignNumber) {
        final List<IncompleteAssignInfoDao> incompleteAssignInfoDaoList = assignQueryService.findAllIncompleteAssignInfoByTodoId(todoId);
        final List<Long> incompleteTodoUserIds = incompleteAssignInfoDaoList.stream()
            .map(IncompleteAssignInfoDao::getUserId)
            .toList();
        final Map<Long, User> incompleteTodoUserMap = userQueryService.findMapWithUserIdKeyByIds(incompleteTodoUserIds);
        return incompleteAssignInfoDaoList.stream()
            .map(incompleteAssignInfo -> {
                final User incompleteTodoUser = incompleteTodoUserMap.get(incompleteAssignInfo.getUserId());
                final String notificationMessage = String.format(NOTIFICATION_MESSAGE, incompleteAssignInfo.getTodoDescription(), completeAssignNumber, incompleteTodoUser.getNickname());
                return new NotificationEvent(incompleteTodoUser.getId(),incompleteAssignInfo.getTodoTeamName(), notificationMessage, todoId);
            })
            .toList();
    }

    private boolean isRemindable(final Long todoId, final long completeAssignNumber){
        final long totalAssignNumber = assignQueryService.countAssignByTodoId(todoId);
        return (float) completeAssignNumber >= (float) totalAssignNumber * 0.5;
    }
}
