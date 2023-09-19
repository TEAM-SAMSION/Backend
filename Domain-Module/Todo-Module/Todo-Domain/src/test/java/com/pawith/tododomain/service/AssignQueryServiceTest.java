package com.pawith.tododomain.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.repository.AssignRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;

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
    @DisplayName("registerId와 날짜를 받아 해당 날짜의 할당 목록을 조회한다.")
    void findAssignByRegisterIdAndCreatedAtBetween() {
        //given
        final Long mockRegisterId = FixtureMonkey.create().giveMeOne(Long.class);
        final LocalDateTime mockStartOfDay = FixtureMonkey.create().giveMeOne(LocalDateTime.class);
        final LocalDateTime mockEndOfDay = FixtureMonkey.create().giveMeOne(LocalDateTime.class);
        final List<Assign> mockAssign = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Assign.class,10);
        given(assignRepository.findAllByRegisterIdAndCreatedAtBetween(mockRegisterId, mockStartOfDay, mockEndOfDay)).willReturn(mockAssign);
        //when
        List<Assign> result = assignQueryService.findAssignByRegisterIdAndCreatedAtBetween(mockRegisterId, mockStartOfDay, mockEndOfDay);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockAssign);
    }

}