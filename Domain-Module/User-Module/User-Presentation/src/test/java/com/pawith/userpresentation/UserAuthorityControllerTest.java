package com.pawith.userpresentation;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.testmodule.BaseRestDocsTest;
import com.pawith.usermodule.application.service.UserAuthorityGetUseCase;
import com.pawith.usermodule.application.service.dto.UserAuthorityInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserAuthorityController.class)
@DisplayName("UserAuthorityController 테스트")
public class UserAuthorityControllerTest extends BaseRestDocsTest {

    private static final String USER_AUTHORITY_URL = "/user/authority";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer ";

    @MockBean
    UserAuthorityGetUseCase userAuthorityGetUseCase;



    @Test
    @DisplayName("사용자 권한 조회")
    void getUserAuthority() throws Exception {
        //given
        final UserAuthorityInfoResponse testUserAuthorityInfoResponse = getFixtureMonkey().giveMeOne(UserAuthorityInfoResponse.class);
        given(userAuthorityGetUseCase.getUserAuthority()).willReturn(testUserAuthorityInfoResponse);
        MockHttpServletRequestBuilder request = get(USER_AUTHORITY_URL)
            .header(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_VALUE + FixtureMonkey.create().giveMeOne(String.class));
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(print())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName(AUTHORIZATION_HEADER).description("access 토큰")
                ),
                responseFields(
                    fieldWithPath("authority").description("사용자 권한")
                )
            ));
    }

    private FixtureMonkey getFixtureMonkey() {
        return FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build();
    }
}
