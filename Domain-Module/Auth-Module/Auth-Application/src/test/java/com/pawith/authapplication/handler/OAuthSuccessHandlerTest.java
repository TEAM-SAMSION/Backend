package com.pawith.authapplication.handler;

import com.pawith.authapplication.handler.request.OAuthSuccessEvent;
import com.pawith.authdomain.entity.OAuth;
import com.pawith.authdomain.exception.OAuthException;
import com.pawith.authdomain.service.OAuthQueryService;
import com.pawith.authdomain.service.OAuthSaveService;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.enums.Provider;
import com.pawith.commonmodule.event.UserSignUpEvent;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@UnitTestConfig
@DisplayName("OAuthSuccessHandler 테스트")
class OAuthSuccessHandlerTest {

    @Mock
    private OAuthQueryService oAuthQueryService;
    @Mock
    private OAuthSaveService oAuthSaveService;
    @Mock
    private UserQueryService userQueryService;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private OAuthSuccessHandler oAuthSuccessHandler;

    @BeforeEach
    void setUp() {
        oAuthSuccessHandler = new OAuthSuccessHandler(oAuthQueryService, oAuthSaveService, userQueryService, applicationEventPublisher);
    }

    @Test
    @DisplayName("소셜 로그인 성공 이벤트를 처리할 때, 기존 요청 sub에 대해 OAuth가 존재하면 User의 email을 업데이트한다.")
    void handleWithExistOAuth() {
        // given
        final OAuthSuccessEvent oAuthSuccessEvent = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(OAuthSuccessEvent.class);
        final User user = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(User.class);
        final OAuth oAuth = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeBuilder(OAuth.class)
            .set("userId", user.getId())
            .sample();
        given(oAuthQueryService.existBySub(oAuthSuccessEvent.sub())).willReturn(true);
        given(oAuthQueryService.findBySub(oAuthSuccessEvent.sub())).willReturn(oAuth);
        given(userQueryService.findById(oAuth.getUserId())).willReturn(user);
        // when
        oAuthSuccessHandler.handle(oAuthSuccessEvent);
        // then
        Assertions.assertThat(user.getEmail()).isEqualTo(oAuthSuccessEvent.email());
    }

    @Nested
    @DisplayName("소셜 로그인 성공 이벤트를 처리할 때, 기존 요청 sub에 대해 OAuth가 존재하지 않으면")
    class Describe_handleWithNotExistOAuth {
        @Test
        @DisplayName("User의 OAuth 정보가 존재하면, OAuthException을 발생시킨다.")
        void handleWithExistEmail() {
            // given
            final OAuthSuccessEvent oAuthSuccessEvent = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(OAuthSuccessEvent.class);
            final User user = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(User.class);
            given(oAuthQueryService.existBySub(oAuthSuccessEvent.sub())).willReturn(false);
            given(userQueryService.checkEmailAlreadyExist(oAuthSuccessEvent.email())).willReturn(true);
            given(userQueryService.findByEmail(oAuthSuccessEvent.email())).willReturn(user);
            given(oAuthQueryService.existByUserId(user.getId())).willReturn(true);
            // when
            // then
            Assertions.assertThatCode(() -> oAuthSuccessHandler.handle(oAuthSuccessEvent))
                .isInstanceOf(OAuthException.class);
        }

        @Test
        @DisplayName("User의 provider 필드가 존재하고, 요청 Provider와 일치하지 않으면 OAuthException을 발생시킨다.")
        void handleWithNotMatchingProvider() {
            // given
            final User user = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(User.class);
            final OAuthSuccessEvent oAuthSuccessEvent = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
                .giveMeBuilder(OAuthSuccessEvent.class)
                .setPostCondition("provider", Provider.class, user::isNotMatchingProvider)
                .sample();
            given(oAuthQueryService.existBySub(oAuthSuccessEvent.sub())).willReturn(false);
            given(userQueryService.checkEmailAlreadyExist(oAuthSuccessEvent.email())).willReturn(true);
            given(userQueryService.findByEmail(oAuthSuccessEvent.email())).willReturn(user);
            // when
            // then
            Assertions.assertThatCode(() -> oAuthSuccessHandler.handle(oAuthSuccessEvent))
                .isInstanceOf(OAuthException.class);
        }


        @Test
        @DisplayName("기존 계정이 존재하는경우 Provider와 일치하는 요청이면 OAuth 정보를 저장한다.")
        void handleWithExistUser() {
            // given
            final User user = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(User.class);
            final OAuthSuccessEvent oAuthSuccessEvent = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
                .giveMeBuilder(OAuthSuccessEvent.class)
                .setPostCondition("provider", Provider.class, user::isMatchingProvider)
                .sample();
            given(oAuthQueryService.existBySub(oAuthSuccessEvent.sub())).willReturn(false);
            given(userQueryService.checkEmailAlreadyExist(oAuthSuccessEvent.email())).willReturn(true);
            given(userQueryService.findByEmail(oAuthSuccessEvent.email())).willReturn(user);
            given(oAuthQueryService.existByUserId(user.getId())).willReturn(false);
            // when
            oAuthSuccessHandler.handle(oAuthSuccessEvent);
            // then
            then(applicationEventPublisher).shouldHaveNoInteractions();
            then(oAuthSaveService).should().save(any(OAuth.class));
        }

        @Test
        @DisplayName("User의 email이 존재하지 않으면,  User와 OAuth 정보를 저장한다.")
        void handleWithNotExistEmail() {
            // given
            final OAuthSuccessEvent oAuthSuccessEvent = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(OAuthSuccessEvent.class);
            final User user = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(User.class);
            given(oAuthQueryService.existBySub(oAuthSuccessEvent.sub())).willReturn(false);
            given(userQueryService.checkEmailAlreadyExist(oAuthSuccessEvent.email())).willReturn(false);
            given(userQueryService.findByEmail(oAuthSuccessEvent.email())).willReturn(user);
            // when
            oAuthSuccessHandler.handle(oAuthSuccessEvent);
            // then
            then(applicationEventPublisher).should().publishEvent(any(UserSignUpEvent.class));
            then(oAuthSaveService).should().save(any(OAuth.class));
        }
    }
}