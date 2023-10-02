package com.pawith.tododomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.repository.AssignRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.BDDMockito.then;
@Slf4j
@UnitTestConfig
@DisplayName("AssignSaveService 테스트")
public class AssignSaveServiceTest {

    @Mock
    private AssignRepository assignRepository;

    private AssignSaveService assignSaveService;

    @BeforeEach
    void init() { assignSaveService = new AssignSaveService(assignRepository); }

    @Test
    @DisplayName("Assign 엔티티를 저장한다.")
    void saveAssignEntity() {
        //given
        final Assign assign = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Assign.class);
        //when
        assignSaveService.saveAssignEntity(assign);
        //then
        then(assignRepository).should().save(assign);
    }
}
