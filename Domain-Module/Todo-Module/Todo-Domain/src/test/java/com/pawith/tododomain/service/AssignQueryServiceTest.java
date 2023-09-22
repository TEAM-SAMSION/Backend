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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
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

//    @Test
//    @DisplayName("registerId와 날짜를 받아 해당 날짜의 할당 목록을 조회한다.")
//    void findAssignByRegisterIdAndCreatedAtBetween() {
//        //given
//        final Pageable pageable = PageRequest.of(0,10);
//        final Long mockRegisterId = FixtureMonkey.create().giveMeOne(Long.class);
//        final List<Assign> mockAssign = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Assign.class,10);
//        final Slice<Assign> mockSlice = new SliceImpl<>(mockAssign, pageable, true);
//        final LocalDate now = LocalDate.now();
//        given(assignRepository.findAllByRegisterIdAndCreatedAtBetween(mockRegisterId, now.atStartOfDay(),now.atTime(LocalTime.MAX), pageable)).willReturn(mockSlice);
//        //when
//        Slice<Assign> result = assignQueryService.findTodayAssignSliceByRegisterId(mockRegisterId, pageable);
//        //then
//       Assertions.assertThat(result.getContent().size()).isEqualTo(pageable.getPageSize());
//    }

}