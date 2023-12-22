package com.pawith.tododomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.repository.AssignRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("AssignQueryService 테스트")
class AssignQueryServiceTest {

    @Mock
    private AssignRepository assignRepository;

    private AssignQueryService assignQueryService;

    @BeforeEach
    void init() {
        assignQueryService = new AssignQueryService(assignRepository);
    }

    @Test
    @DisplayName("카테고리 id와 날짜 기준으로 Assign 목록 조회 테스트")
    void findAllAssignByCategoryIdAndScheduledDate() {
        // given
        final Long categoryId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final LocalDate scheduledDate = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(LocalDate.class);
        final List<Assign> assigns = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Assign.class, 10);
        given(assignRepository.findAllByCategoryIdAndScheduledDateQuery(categoryId, scheduledDate)).willReturn(assigns);
        // when
        final List<Assign> result = assignQueryService.findAllAssignByCategoryIdAndScheduledDate(categoryId, scheduledDate);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(assigns);
    }

    @Test
    @DisplayName("userId와 todoTeamId, 날짜 기준으로 Assign 목록 조회 테스트")
    void findAllByUserIdAndTodoTeamIdAndScheduledDate() {
        // given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final List<Assign> assigns = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Assign.class, 10);
        given(assignRepository.findAllByUserIdAndTodoTeamIdAndScheduledDateQuery(any(), any(), any(LocalDate.class))).willReturn(assigns);
        // when
        final List<Assign> result = assignQueryService.findAllByUserIdAndTodoTeamIdAndScheduledDate(userId, todoTeamId);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(assigns);
    }

    @Test
    @DisplayName("todoId와 userId로 Assign 조회 테스트")
    void findAssignByTodoIdAndUserId() {
        // given
        final Long todoId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Assign assign = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Assign.class);
        given(assignRepository.findByTodoIdAndUserIdQuery(todoId, userId)).willReturn(Optional.of(assign));
        // when
        final Assign result = assignQueryService.findAssignByTodoIdAndUserId(todoId, userId);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(assign);
    }

    @Test
    @DisplayName("todoId로 Assign 목록 조회 테스트")
    void findAllAssignByTodoId() {
        // given
        final Long todoId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final List<Assign> assigns = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Assign.class, 10);
        given(assignRepository.findAllByTodoIdQuery(todoId)).willReturn(assigns);
        // when
        final List<Assign> result = assignQueryService.findAllAssignByTodoId(todoId);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(assigns);
    }

}