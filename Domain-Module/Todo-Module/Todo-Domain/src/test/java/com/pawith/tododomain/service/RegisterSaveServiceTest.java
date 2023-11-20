package com.pawith.tododomain.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.exception.AlreadyRegisterTodoTeamException;
import com.pawith.tododomain.repository.RegisterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@UnitTestConfig
@DisplayName("RegisterSaveService 테스트")
class RegisterSaveServiceTest {

    @Mock
    private RegisterRepository registerRepository;

    private RegisterSaveService registerSaveService;

    @BeforeEach
    void init() {
        registerSaveService = new RegisterSaveService(registerRepository);
    }

    @Test
    @DisplayName("todoTeam 엔티티와 userId를 입력받아 member 권한 Register 엔티티를 저장한다.")
    void saveRegisterAboutMember() {
        //given
        final TodoTeam todoTeam = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(TodoTeam.class);
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        //when
        registerSaveService.saveRegisterAboutMember(todoTeam, userId);
        //then
        then(registerRepository).should().save(any());
    }

    @Test
    @DisplayName("todoTeam 엔티티와 userId를 입력받아 president 권한 Register 엔티티를 저장한다.")
    void saveRegisterAboutPresident() {
        //given
        final TodoTeam todoTeam = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(TodoTeam.class);
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        given(registerRepository.existsByTodoTeamIdAndUserIdAndIsRegistered(todoTeam.getId(), userId,true)).willReturn(false);
        //when
        registerSaveService.saveRegisterAboutPresident(todoTeam, userId);
        //then
        then(registerRepository).should().save(any());
    }

    @Test
    @DisplayName("todoTeam 엔티티와 userId를 입력받아 이미 등록된 회원이면 AlreadyRegisterTodoTeamException이 발생한다.")
    void saveRegisterAboutMemberFail() {
        //given
        final TodoTeam todoTeam = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(TodoTeam.class);
        final Long userId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        given(registerRepository.existsByTodoTeamIdAndUserIdAndIsRegistered(todoTeam.getId(), userId,false)).willReturn(false);
        given(registerRepository.existsByTodoTeamIdAndUserIdAndIsRegistered(todoTeam.getId(), userId,true)).willReturn(true);
        //when
        //then
        Assertions.assertThatCode(() -> registerSaveService.saveRegisterAboutMember(todoTeam, userId))
                .isInstanceOf(AlreadyRegisterTodoTeamException.class);
    }
}