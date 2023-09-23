package com.pawith.tododomain.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.exception.NotRegisterUserException;
import com.pawith.tododomain.repository.RegisterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("RegisterQueryService 테스트")
class RegisterQueryServiceTest {

    @Mock
    private RegisterRepository registerRepository;

    private RegisterQueryService registerQueryService;

    @BeforeEach
    void init(){
        registerQueryService = new RegisterQueryService(registerRepository);
    }

    @Test
    @DisplayName("todoTeamId와 userId로 Register 엔티티를 조회한다.")
    void findByTodoTeamIdAndUserId() {
        //given
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final Long userId = FixtureMonkey.create().giveMeOne(Long.class);
        final Register mockRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Register.class);
        given(registerRepository.findByTodoTeamIdAndUserId(todoTeamId, userId)).willReturn(Optional.of(mockRegister));
        //when
        Register result = registerQueryService.findRegisterByTodoTeamIdAndUserId(todoTeamId, userId);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockRegister);
    }

    @Test
    @DisplayName("todoTeamId와 userId로 Register 엔티티를 조회하지 못하면 NotRegisterUserException이 발생한다.")
    void findByTodoTeamIdAndUserIdFail() {
        //given
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final Long userId = FixtureMonkey.create().giveMeOne(Long.class);
        given(registerRepository.findByTodoTeamIdAndUserId(todoTeamId, userId)).willReturn(Optional.empty());
        //when
        //then
        Assertions.assertThatCode(() -> registerQueryService.findRegisterByTodoTeamIdAndUserId(todoTeamId, userId))
            .isInstanceOf(NotRegisterUserException.class);
    }

    @Test
    @DisplayName("userId와 Pageable로 Register 엔티티를 조회한다.")
    void findRegisterSliceByUserId() {
        //given
        final Long userId = FixtureMonkey.create().giveMeOne(Long.class);
        final PageRequest pageRequest = PageRequest.of(0, 10);
        final List<Register> mockRegister = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Register.class, 10);
        SliceImpl<Register> mockSlice = new SliceImpl<>(mockRegister, pageRequest, true);
        given(registerRepository.findAllByUserId(userId,pageRequest)).willReturn(mockSlice);
        //when
        Slice<Register> result = registerQueryService.findRegisterSliceByUserId(userId,pageRequest);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockSlice);
    }

}