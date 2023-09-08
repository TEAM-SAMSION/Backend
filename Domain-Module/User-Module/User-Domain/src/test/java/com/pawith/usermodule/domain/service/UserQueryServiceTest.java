package com.pawith.usermodule.domain.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.usermodule.domain.entity.User;
import com.pawith.usermodule.domain.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("UserQueryService 테스트")
public class UserQueryServiceTest {

    @Mock
    UserRepository userRepository;
    UserQueryService userQueryService;
    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .build();


    private static final String PROVIDER = "provider";
    private static final String EMAIL = "email";

    @BeforeEach
    void init() { userQueryService = new UserQueryService(userRepository); }

    @Test
    @DisplayName("email을 통해 사용자정보를 가져온다.")
    void findByEmail() {
        //given
        User mockUser = getMockUser();
        given(userRepository.findByEmail(any())).willReturn(Optional.of(mockUser));
        //when
        User user = userQueryService.findByEmail(mockUser.getEmail());
        //then
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user).usingRecursiveComparison().isEqualTo(mockUser);
    }


    User getMockUser() {
        return fixtureMonkey.giveMeBuilder(User.class)
                .set("email", EMAIL)
                .set("provider", PROVIDER)
                .sample();
    }



}
