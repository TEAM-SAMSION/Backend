package com.pawith.userapplication.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.security.WithMockAuthUser;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.userapplication.dto.response.UserAuthorityInfoResponse;
import com.pawith.userdomain.entity.UserAuthority;
import com.pawith.userdomain.service.UserAuthorityQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("UserAuthorityGetUseCase 테스트")
class UserAuthorityGetUseCaseTest {

    @Mock
    UserAuthorityQueryService userAuthorityQueryService;

    UserAuthorityGetUseCase userAuthorityGetUseCase;

    @BeforeEach
    void init() {
        userAuthorityGetUseCase = new UserAuthorityGetUseCase(userAuthorityQueryService);
    }

    @Test
    @WithMockAuthUser
    @DisplayName("유저 권한을 조회한다.")
    void getUserAuthority() {
        //given
        final UserAuthority userAuthority = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeBuilder(UserAuthority.class)
            .sample();
        given(userAuthorityQueryService.findByUserId(anyLong())).willReturn(userAuthority);
        //when
        UserAuthorityInfoResponse result = userAuthorityGetUseCase.getUserAuthority();
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(new UserAuthorityInfoResponse(userAuthority.getAuthority()));
    }

}