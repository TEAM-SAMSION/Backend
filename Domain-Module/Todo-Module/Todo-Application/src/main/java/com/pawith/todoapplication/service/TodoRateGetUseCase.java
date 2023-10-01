package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.response.TodoProgressResponse;
import com.pawith.todoapplication.dto.response.TodoRateCompareResponse;
import com.pawith.tododomain.service.TodoQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoRateGetUseCase {

    private final UserUtils userUtils;
    private final TodoQueryService todoQueryService;

    /**
     * 리팩터링 전, 100명 동시 요청 평균 : 426ms
     * <br>리팩터링 후, 100명 동시 요청 평균 : 67ms(535% 성능개선)
     */
    public TodoProgressResponse getTodoProgress(final Long todoTeamId) {
        final User user = userUtils.getAccessUser();
        final Integer todoCompleteRate = todoQueryService.findTodoCompleteRate(user.getId(), todoTeamId);
        return new TodoProgressResponse(todoCompleteRate);
    }

    public TodoRateCompareResponse getWeekProgressCompare(final Long todoTeamId) {
        final User user = userUtils.getAccessUser();
        final Integer thisWeekTodoCompleteRate = todoQueryService.findThisWeekTodoCompleteRate(user.getId(), todoTeamId);
        final Integer lastWeekTodoCompleteRate = todoQueryService.findLastWeekTodoCompleteRate(user.getId(), todoTeamId);
        return new TodoRateCompareResponse(thisWeekTodoCompleteRate > lastWeekTodoCompleteRate, thisWeekTodoCompleteRate.equals(lastWeekTodoCompleteRate));
    }
}
