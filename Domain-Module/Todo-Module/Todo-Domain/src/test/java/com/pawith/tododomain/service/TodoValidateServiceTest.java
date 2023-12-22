package com.pawith.tododomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.entity.Todo;
import com.pawith.tododomain.exception.TodoModificationNotAllowedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@UnitTestConfig
@DisplayName("TodoValidateService 테스트")
class TodoValidateServiceTest {

    private TodoValidateService todoValidateService;

    @BeforeEach
    void init() {
        todoValidateService = new TodoValidateService();
    }

    @Test
    @DisplayName("todo 생성자가 아니면서 멤버이면 true를 반환한다.")
    void validateDeleteAndUpdate_throw_ToDoModificationNotAllowedException() {
        // given
        final Boolean isNotValidate = true;
        final Register register = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("authority", Authority.MEMBER)
            .sample();
        final Todo todo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Todo.class)
            .set("creatorId",register.getId()-1)
            .sample();
        // when
        // then
        Assertions.assertThat(todoValidateService.validateDeleteAndUpdate(todo, register)).isEqualTo(isNotValidate);
    }


    @Test
    @DisplayName("todo 생성자이면 false를 반환한다.")
    void validateDeleteAndUpdate_throw_NoException() {
        // given
        final Boolean isNotValidate = false;
        final Register register = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("authority", Authority.MEMBER)
            .sample();
        final Todo todo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Todo.class)
            .set("creatorId",register.getId())
            .sample();
        // when
        // then
        Assertions.assertThat(todoValidateService.validateDeleteAndUpdate(todo, register)).isEqualTo(isNotValidate);
    }

    @Test
    @DisplayName("todo 생성자가 아니면서 운영진이면 false를 반환한다.")
    void validateDeleteAndUpdate_throw_NoException2() {
        // given
        final Boolean isNotValidate = false;
        final Register register = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Register.class)
            .set("authority", Authority.PRESIDENT)
            .sample();
        final Todo todo = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Todo.class)
            .set("creatorId",register.getId()-1)
            .sample();
        // when
        // then
        Assertions.assertThat(todoValidateService.validateDeleteAndUpdate(todo, register)).isEqualTo(isNotValidate);
    }

}