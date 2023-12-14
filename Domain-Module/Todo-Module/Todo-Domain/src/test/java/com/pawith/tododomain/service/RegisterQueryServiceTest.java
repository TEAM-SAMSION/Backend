package com.pawith.tododomain.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.exception.NotRegisterUserException;
import com.pawith.tododomain.repository.RegisterRepository;
import com.pawith.tododomain.utils.RegisterTestFixtureEntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@Slf4j
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
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Register mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntity();
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
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
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
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final PageRequest pageRequest = PageRequest.of(0, 10);
        final List<Register> mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntityList(pageRequest.getPageSize());
        SliceImpl<Register> mockSlice = new SliceImpl<>(mockRegister, pageRequest, true);
        given(registerRepository.findAllByUserIdQuery(userId,pageRequest)).willReturn(mockSlice);
        //when
        Slice<Register> result = registerQueryService.findRegisterSliceByUserId(userId,pageRequest);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockSlice);
    }

    @Test
    @DisplayName("userId와 Pageable로 Register 엔티티를 조회한다. 탈퇴한 todo team은 조회되지 않는다.")
    void findRegisterSliceByUserIdWithUnregistered() {
        //given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final PageRequest pageRequest = PageRequest.of(0, 10);
        SliceImpl<Register> mockSlice = new SliceImpl<>(List.of(), pageRequest, false);
        given(registerRepository.findAllByUserIdQuery(userId,pageRequest)).willReturn(mockSlice);
        //when
        Slice<Register> result = registerQueryService.findRegisterSliceByUserId(userId,pageRequest);
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("todoTeamId와 authority로 Register 엔티티를 조회한다.")
    void findByTodoTeamIdAndAuthority() {
        //given
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Register mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntity();
        given(registerRepository.findByTodoTeamIdAndAuthority(todoTeamId, Authority.PRESIDENT)).willReturn(Optional.of(mockRegister));
        //when
        Register result = registerQueryService.findPresidentRegisterByTodoTeamId(todoTeamId);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockRegister);
    }

    @Test
    @DisplayName("todoTeamId와 authority로 Register 엔티티를 조회하지 못하면 NotRegisterUserException이 발생한다.")
    void findByTodoTeamIdAndAuthorityFail() {
        //given
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        given(registerRepository.findByTodoTeamIdAndAuthority(todoTeamId, Authority.PRESIDENT)).willReturn(Optional.empty());
        //when
        //then
        Assertions.assertThatCode(() -> registerQueryService.findPresidentRegisterByTodoTeamId(todoTeamId))
            .isInstanceOf(NotRegisterUserException.class);
    }

    @Test
    @DisplayName("registerIds로 Register 엔티티를 조회한다.")
    void findAllRegisterByIds() {
        //given
        final List<Long> registerIds = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMe(Long.class,10);
        final List<Register> mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntityList(registerIds.size());
        given(registerRepository.findAllByIdsQuery(registerIds)).willReturn(mockRegister);
        //when
        List<Register> result = registerQueryService.findAllRegistersByIds(registerIds);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockRegister);
    }

    @Test
    @DisplayName("registerIds로 Register 엔티티를 조회한다. 탈퇴한 todo team은 조회되지 않는다.")
    void findAllRegisterByIdsWithUnregistered() {
        //given
        final List<Long> registerIds = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMe(Long.class,10);
        final List<Register> mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntityListWithUnregistered(registerIds.size());
        given(registerRepository.findAllByIdsQuery(registerIds)).willReturn(mockRegister);
        //when
        List<Register> result = registerQueryService.findAllRegistersByIds(registerIds);
        //then
        Assertions.assertThat(result.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("UserId와 todoTeamId로 Register 엔티티를 조회한다")
    void findAllRegisters() {
        //given
        final Long userId = FixtureMonkey.create().giveMeOne(Long.class);
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final List<Register> mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntityList(5);
        given(registerRepository.findAllByTodoTeamId(todoTeamId)).willReturn(mockRegister);
        //when
        List<Register> result = registerQueryService.findAllRegistersByTodoTeamId(todoTeamId);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockRegister);
    }

    @Test
    @DisplayName("UserId와 todoTeamId로 Register 엔티티를 조회한다. 존재하지 않는다면 빈 리스트를 반환한다.")
    void findAllRegistersWhenDoesNotExist() {
        //given
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        //when
        List<Register> result = registerQueryService.findAllRegistersByTodoTeamId(todoTeamId);
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("todoTeamId로 Register 엔티티의 수를 조회한다.")
    void countRegisterByTodoTeamId() {
        //given
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Integer mockCount = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Integer.class);
        given(registerRepository.countByTodoTeamIdQuery(todoTeamId)).willReturn(mockCount);
        //when
        Integer result = registerQueryService.countRegisterByTodoTeamId(todoTeamId);
        //then
        Assertions.assertThat(result).isEqualTo(mockCount);
    }


    @Test
    @DisplayName("todoTeam 가입 기간을 조회한다")
    void findUserRegisterTerm() {
        //given
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Register mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntity();
        final LocalDate mockDate = LocalDate.now();
        given(registerRepository.findByTodoTeamIdAndUserId(todoTeamId, userId)).willReturn(Optional.of(mockRegister));
        //when
        Integer result = registerQueryService.findUserRegisterTerm(todoTeamId, userId);
        //then
        Assertions.assertThat(result).isEqualTo((int) ChronoUnit.DAYS.between(mockRegister.getCreatedAt().toLocalDate(), mockDate));
    }

    @Test
    @DisplayName("userId로 가장 최근에 가입한 todoteam의 register를 조회한다.")
    void findLatestTodoTeam() {
        //given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Register mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntity();
        given(registerRepository.findLatestRegisterByUserIdQuery(userId)).willReturn(Optional.of(mockRegister));
        //when
        Register result = registerQueryService.findLatestTodoTeam(userId);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockRegister);
    }

    @Test
    @DisplayName("userId로 가장 최근에 가입한 todoteam의 register를 조회하지 못하면 NotRegisterUserException이 발생한다.")
    void findLatestTodoTeamFail() {
        //given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        given(registerRepository.findLatestRegisterByUserIdQuery(userId)).willReturn(Optional.empty());
        //when
        //then
        Assertions.assertThatCode(() -> registerQueryService.findLatestTodoTeam(userId))
            .isInstanceOf(NotRegisterUserException.class);
    }

    @Test
    @DisplayName("userId로 가입한 todo team의 register 리스트를 반환한다.")
    void findRegisterListByUserIdWithTodoTeam() {
        //given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final List<Register> mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntityList(5);
        given(registerRepository.findAllByUserId(userId)).willReturn(mockRegister);
        //when
        List<Register> result = registerQueryService.findAllRegistersByUserId(userId);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockRegister);
    }

    @Test
    @DisplayName("userId로 가입한 todo team의 register 리스트를 반환한다. 탈퇴할 경우 조회되지 않는다.")
    void findRegisterListByUserIdWithTodoTeamWithUnregistered() {
        //given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final List<Register> mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntityListWithUnregistered(5);
        given(registerRepository.findAllByUserId(userId)).willReturn(mockRegister);
        //when
        List<Register> result = registerQueryService.findAllRegistersByUserId(userId);
        //then
        Assertions.assertThat(result.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("userId로 가입한 todo team의 register 리스트를 반환한다. 해당 메소드는 todoteam과 fetch join된 상태이다.")
    void findRegisterListByUserIdWithTodoTeamFetch() {
        //given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final List<Register> mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntityList(5);
        given(registerRepository.findAllByUserIdWithTodoTeamFetchQuery(userId)).willReturn(mockRegister);
        //when
        List<Register> result = registerQueryService.findRegisterListByUserIdWithTodoTeam(userId);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockRegister);
    }

    @Test
    @DisplayName("userId로 가입한 todo team의 register 리스트를 반환한다. 해당 메소드는 todoteam과 fetch join된 상태이다. 탈퇴할 경우 조회되지 않는다.")
    void findRegisterListByUserIdWithTodoTeamFetchWithUnregistered() {
        //given
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final List<Register> mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntityListWithUnregistered(5);
        given(registerRepository.findAllByUserIdWithTodoTeamFetchQuery(userId)).willReturn(mockRegister);
        //when
        List<Register> result = registerQueryService.findRegisterListByUserIdWithTodoTeam(userId);
        //then
        Assertions.assertThat(result.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("registerId로 register를 조회한다.")
    void findRegisterById() {
        //given
        final Long registerId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Register mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntity();
        given(registerRepository.findById(registerId)).willReturn(Optional.of(mockRegister));
        //when
        Register result = registerQueryService.findRegisterById(registerId);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockRegister);
    }

    @Test
    @DisplayName("registerId로 register를 조회한다. 탈퇴할 경우 조회되지 않는다.")
    void findRegisterByIdWithUnregistered() {
        //given
        final Long registerId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Register mockRegister = RegisterTestFixtureEntityUtils.getRegisterEntityWithUnregistered();
        given(registerRepository.findById(registerId)).willReturn(Optional.of(mockRegister));
        //when
        //then
        Assertions.assertThatCode(() -> registerQueryService.findRegisterById(registerId))
            .isInstanceOf(NotRegisterUserException.class);
    }



}