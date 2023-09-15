package com.pawith.userpresentation;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.usermodule.dto.request.UserNicknameChangeRequest;
import com.pawith.usermodule.dto.response.UserInfoResponse;
import com.pawith.usermodule.service.UserInfoGetUseCase;
import com.pawith.usermodule.service.UserNicknameChangeUseCase;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("User Controller 테스트")
class UserControllerTest extends BaseRestDocsTest {

    @MockBean
    private UserNicknameChangeUseCase userNicknameChangeUseCase;
    @MockBean
    private UserInfoGetUseCase userInfoGetUseCase;

    private static final String USER_REQUEST_URL = "/user";
    private static final String ACCESS_TOKEN = "Bearer accessToken";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Test
    @DisplayName("닉네임 변경 테스트")
    void putNicknameOnUser() throws Exception {
        //given
        final UserNicknameChangeRequest userNicknameChangeRequest = new UserNicknameChangeRequest("requestNickname");
        final MockHttpServletRequestBuilder request = put(USER_REQUEST_URL + "/name")
            .contentType("application/json")
            .header(AUTHORIZATION_HEADER, ACCESS_TOKEN)
            .content(objectMapper.writeValueAsString(userNicknameChangeRequest));
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName(AUTHORIZATION_HEADER).description("access 토큰")
                ),
                requestFields(
                    fieldWithPath("nickname").description("변경할 닉네임")
                )
            ));
    }

    @Test
    @DisplayName("유저 정보 조회 테스트")
    void getUserInfo() throws Exception {
        //given
        final MockHttpServletRequestBuilder request = get(USER_REQUEST_URL)
            .contentType("application/json")
            .header(AUTHORIZATION_HEADER, ACCESS_TOKEN);
        final FixtureMonkey fixtureMonkey = FixtureMonkey.builder().defaultNotNull(true).objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE).build();
        given(userInfoGetUseCase.getUserInfo()).willReturn(fixtureMonkey.giveMeOne(UserInfoResponse.class));
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName(AUTHORIZATION_HEADER).description("access 토큰")
                ),
                responseFields(
                    fieldWithPath("nickname").description("유저 닉네임"),
                    fieldWithPath("email").description("유저 이메일"),
                    fieldWithPath("profileImageUrl").description("유저 프로필 이미지")
                )
            ));
    }
}