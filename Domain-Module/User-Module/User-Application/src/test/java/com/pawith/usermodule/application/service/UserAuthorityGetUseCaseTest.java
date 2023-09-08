package com.pawith.usermodule.application.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.security.WithMockAuthUser;
import com.pawith.usermodule.application.service.dto.UserAuthorityInfoResponse;
import com.pawith.usermodule.domain.entity.UserAuthority;
import com.pawith.usermodule.domain.service.UserAuthorityQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("UserAuthorityGetUseCase 테스트")
class UserAuthorityGetUseCaseTest {

    @Mock
    UserAuthorityQueryService userAuthorityQueryService;

    UserAuthorityGetUseCase userAuthorityGetUseCase;

    private static final String MOCK_EMAIL = "email";

    @BeforeEach
    void init() {
        userAuthorityGetUseCase = new UserAuthorityGetUseCase(userAuthorityQueryService);
    }

    @Test
    @WithMockAuthUser
    @DisplayName("유저 권한을 조회한다.")
    void getUserAuthority() {
        //given
        final UserAuthority userAuthority = FixtureMonkey.builder().objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE).defaultNotNull(true).build()
            .giveMeBuilder(UserAuthority.class)
            .sample();
        given(userAuthorityQueryService.findByEmail(anyString())).willReturn(userAuthority);
        //when
        UserAuthorityInfoResponse result = userAuthorityGetUseCase.getUserAuthority();
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(new UserAuthorityInfoResponse(userAuthority.getAuthority()));
    }

}