package com.pawith.userpresentation;

import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.userapplication.dto.request.PathHistoryCreateRequest;
import com.pawith.userapplication.dto.request.UserNicknameChangeRequest;
import com.pawith.userapplication.dto.request.WithdrawReasonCreateRequest;
import com.pawith.userapplication.dto.response.UserInfoResponse;
import com.pawith.userapplication.dto.response.UserJoinTermResponse;
import com.pawith.userapplication.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("User Controller 테스트")
class UserControllerTest extends BaseRestDocsTest {

    @MockBean
    private UserNicknameChangeUseCase userNicknameChangeUseCase;
    @MockBean
    private UserInfoGetUseCase userInfoGetUseCase;
    @MockBean
    private UserProfileImageUpdateUseCase userProfileImageUpdateUseCase;
    @MockBean
    private PathHistoryCreateUseCase pathHistoryCreateUseCase;
    @MockBean
    private UserDeleteUseCase userDeleteUseCase;
    @MockBean
    private WithdrawReasonCreateUseCase withdrawReasonCreateUseCase;

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
        given(userInfoGetUseCase.getUserInfo()).willReturn(FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(UserInfoResponse.class));
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

    @Test
    @DisplayName("유저 프로필 이미지 업데이트 테스트")
    void postUserProfileImage() throws Exception {
        //given
        final MockMultipartFile profileImage = new MockMultipartFile("profileImage", "profileImage".getBytes());
        final MockHttpServletRequestBuilder request = multipart(USER_REQUEST_URL)
            .file(profileImage)
            .contentType("multipart/form-data")
            .header(AUTHORIZATION_HEADER, ACCESS_TOKEN);
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName(AUTHORIZATION_HEADER).description("access 토큰")
                ),
                requestParts(
                    partWithName("profileImage").description("유저 프로필 이미지")
                )
            ));
    }

    @Test
    @DisplayName("유저 알게 된 경로 히스토리 생성 테스트")
    void postPathHistory() throws Exception {
        //given
        final PathHistoryCreateRequest pathHistoryCreateRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(PathHistoryCreateRequest.class);
        final MockHttpServletRequestBuilder request = post(USER_REQUEST_URL + "/path")
            .content(objectMapper.writeValueAsString(pathHistoryCreateRequest))
            .contentType("application/json")
            .header(AUTHORIZATION_HEADER, ACCESS_TOKEN);
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName(AUTHORIZATION_HEADER).description("access 토큰")
                ),
                requestFields(
                    fieldWithPath("path").description("유저가 포잇을 알게 된 경로")
                )
            ));
    }

    @Test
    @DisplayName("사용자 탈퇴 테스트")
    void deleteUser() throws Exception {
        //given
        MockHttpServletRequestBuilder request = delete(USER_REQUEST_URL)
            .header(AUTHORIZATION_HEADER, ACCESS_TOKEN);
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName(AUTHORIZATION_HEADER).description("access 토큰")
                )
            ));
    }

    @Test
    @DisplayName("서비스 가입 조회 테스트")
    void getTerm() throws Exception {
        //given
        final MockHttpServletRequestBuilder request = get(USER_REQUEST_URL + "/term")
            .contentType("application/json")
            .header(AUTHORIZATION_HEADER, ACCESS_TOKEN);
        given(userInfoGetUseCase.getTerm()).willReturn(FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(
                UserJoinTermResponse.class));
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName(AUTHORIZATION_HEADER).description("access 토큰")
                ),
                responseFields(
                    fieldWithPath("joinTerm").description("서비스 가입 기간")
                )
            ));
    }

    @Test
    @DisplayName("서비스 탈퇴 이유 저장 API 테스트")
    void postWithdrawReason() throws Exception {
        //given
        final WithdrawReasonCreateRequest withdrawReasonCreateRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(WithdrawReasonCreateRequest.class);
        final MockHttpServletRequestBuilder request = post(USER_REQUEST_URL + "/withdraw")
            .content(objectMapper.writeValueAsString(withdrawReasonCreateRequest))
            .contentType("application/json")
            .header(AUTHORIZATION_HEADER, ACCESS_TOKEN);
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName(AUTHORIZATION_HEADER).description("access 토큰")
                ),
                requestFields(
                    fieldWithPath("reason").description("탈퇴 이유")
                )
            ));
    }
}