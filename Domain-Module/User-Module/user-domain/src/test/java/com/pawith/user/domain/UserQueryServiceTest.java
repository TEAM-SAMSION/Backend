package com.pawith.user.domain;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.usermodule.domain.entity.User;
import com.pawith.usermodule.domain.exception.AccountAlreadyExistException;
import com.pawith.usermodule.domain.repository.UserRepository;
import com.pawith.usermodule.domain.service.UserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@Slf4j
@ExtendWith(MockitoExtension.class)
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
        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(mockUser));
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
