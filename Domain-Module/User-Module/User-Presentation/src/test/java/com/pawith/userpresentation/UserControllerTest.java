package com.pawith.userpresentation;

import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.security.WithMockAuthUser;
import com.pawith.usermodule.service.UserNicknameChangeUseCase;
import com.pawith.usermodule.service.dto.UserNicknameChangeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("User Controller 테스트")
class UserControllerTest extends BaseRestDocsTest {

    @MockBean
    private UserNicknameChangeUseCase userNicknameChangeUseCase;

    private static final String USER_REQUEST_URL = "/user";
    private static final String ACCESS_TOKEN = "Bearer accessToken";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Test
    @WithMockAuthUser
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
}