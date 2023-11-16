package com.pawith.todopresentation;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.request.AuthorityChangeRequest;
import com.pawith.todoapplication.dto.response.*;
import com.pawith.todoapplication.service.ChangeRegisterUseCase;
import com.pawith.todoapplication.service.RegistersGetUseCase;
import com.pawith.todoapplication.service.TodoTeamRegisterUseCase;
import com.pawith.todoapplication.service.UnregisterUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegisterController.class)
@DisplayName("RegisterController 테스트")
class RegisterControllerTest extends BaseRestDocsTest {

    @MockBean
    private UnregisterUseCase unregisterUseCase;
    @MockBean
    private TodoTeamRegisterUseCase todoTeamRegisterUseCase;
    @MockBean
    private RegistersGetUseCase registersGetUseCase;
    @MockBean
    private ChangeRegisterUseCase changeRegisterUseCase;

    private static final String REGISTER_REQUEST_URL = "/teams";
    private static final String Authrotity = "EXECUTIVE";

    @Test
    @DisplayName("TodoTeamId로 TodoTeam을 삭제하는 테스트")
    void unregisterTodoTeam() throws Exception {
        // given
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        MockHttpServletRequestBuilder request = RestDocumentationRequestBuilders.delete(REGISTER_REQUEST_URL + "/{todoTeamId}/registers", todoTeamId)
            .header("Authorization", "Bearer accessToken");
        // when
        ResultActions result = mvc.perform(request);
        // then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                pathParameters(
                    parameterWithName("todoTeamId").description("삭제할 TodoTeam의 Id")
                )
            ));
    }

    @Test
    @DisplayName("TodoTeamCode로 TodoTeam을 등록하는 테스트")
    void postRegister() throws Exception{
        //given
        final String todoTeamCode = UUID.randomUUID().toString().split("-")[0];
        MockHttpServletRequestBuilder request = post(REGISTER_REQUEST_URL+"/registers")
            .queryParam("todoTeamCode", todoTeamCode)
            .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                queryParameters(
                    parameterWithName("todoTeamCode").description("TodoTeam의 코드")
                )
            ));
    }

    @Test
    @DisplayName("TodoTeam의 등록된 Register들을 조회하는 테스트")
    void getRegisters() throws Exception {
        //given
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final List<RegisterInfoResponse> registerInfoResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMe(RegisterInfoResponse.class, 10);
        given(registersGetUseCase.getRegisters(todoTeamId)).willReturn(ListResponse.from(registerInfoResponses));
        MockHttpServletRequestBuilder request = get(REGISTER_REQUEST_URL + "/{todoTeamId}/registers",todoTeamId)
            .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                pathParameters(
                    parameterWithName("todoTeamId").description("조회하는 TodoTeamId")
                ),
                responseFields(
                    fieldWithPath("content[].registerId").description("TodoTeam에 등록된 사용자 registerId"),
                    fieldWithPath("content[].registerName").description("TodoTeam에 등록된 사용자 이메일")
                )
            ));

    }


    @Test
    @DisplayName("TodoTeam의 등록된 Register들을 조회하는 테스트 (관리자 페이지에서 사용)")
    void getManageRegisters() throws Exception {
        //given
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final List<RegisterManageInfoResponse> registerManageInfoResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMe(RegisterManageInfoResponse.class, 10);
        given(registersGetUseCase.getManageRegisters(todoTeamId)).willReturn(ListResponse.from(registerManageInfoResponses));
        MockHttpServletRequestBuilder request = get(REGISTER_REQUEST_URL + "/{todoTeamId}/registers/manage",todoTeamId)
                .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
                .andDo(resultHandler.document(
                        requestHeaders(
                                headerWithName("Authorization").description("access 토큰")
                        ),
                        pathParameters(
                                parameterWithName("todoTeamId").description("조회하는 TodoTeamId")
                        ),
                        responseFields(
                                fieldWithPath("content[].registerId").description("TodoTeam에 등록된 사용자 registerId"),
                                fieldWithPath("content[].authority").description("TodoTeam에 등록된 사용자 권한"),
                                fieldWithPath("content[].registerName").description("TodoTeam에 등록된 사용자 이름"),
                                fieldWithPath("content[].registerEmail").description("TodoTeam에 등록된 사용자 이메일"),
                                fieldWithPath("content[].profileImage").description("TodoTeam에 등록된 사용자 프로필 이미지")
                        )
                ));

    }

    @Test
    @DisplayName("Register의 권한을 변경하는 테스트")
    void putAuthority() throws Exception {
        //given
        final Long registerId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final AuthorityChangeRequest authorityChangeRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(AuthorityChangeRequest.class);
        MockHttpServletRequestBuilder request = put(REGISTER_REQUEST_URL + "/{todoTeamId}/registers/{registerId}", todoTeamId, registerId)
            .contentType("application/json")
            .header("Authorization", "Bearer accessToken")
            .content(objectMapper.writeValueAsString(authorityChangeRequest));
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                pathParameters(
                    parameterWithName("todoTeamId").description("변경할 TodoTeam의 Id"),
                    parameterWithName("registerId").description("변경할 register의 Id")
                ),
                requestFields(
                    fieldWithPath("authority").description("변경할 권한")
                )
            ));
    }


    @Test
    @DisplayName("팀 가입 기간 조회 API 테스트")
    void getRegisterTerm() throws Exception {
        //given
        final Long todoTeamId = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Long.class);
        final Integer registerTerm = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Integer.class);
        final RegisterTermResponse registerTermResponse = new RegisterTermResponse(registerTerm);
        given(registersGetUseCase.getRegisterTerm(todoTeamId)).willReturn(registerTermResponse);
        MockHttpServletRequestBuilder request = get(REGISTER_REQUEST_URL + "/{todoTeamId}/registers/term", todoTeamId)
            .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                pathParameters(
                    parameterWithName("todoTeamId").description("TodoTeam의 Id")
                ),
                responseFields(
                    fieldWithPath("registerTerm").description("팀 가입한 기간")
                )
            ));
    }

    @Test
    @DisplayName("닉네임으로 Register를 검색하는 테스트")
    void getRegisterByNickname() throws Exception {
        //given
        final Long todoTeamId = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Long.class);
        final String nickname = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final List<RegisterSearchInfoResponse> registerSearchInfoResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMe(RegisterSearchInfoResponse.class, 2);
        given(registersGetUseCase.searchRegisterByNickname(todoTeamId, nickname)).willReturn(ListResponse.from(registerSearchInfoResponses));
        MockHttpServletRequestBuilder request = get(REGISTER_REQUEST_URL + "/{todoTeamId}/registers/search", todoTeamId)
            .queryParam("nickname", nickname)
            .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                pathParameters(
                    parameterWithName("todoTeamId").description("TodoTeam의 Id")
                ),
                queryParameters(
                    parameterWithName("nickname").description("검색할 Register의 nickname")
                ),
                responseFields(
                    fieldWithPath("content[].registerId").description("Register의 Id"),
                    fieldWithPath("content[].authority").description("Register의 권한"),
                    fieldWithPath("content[].registerName").description("Register의 이름"),
                    fieldWithPath("content[].registerEmail").description("Register의 이메일"),
                    fieldWithPath("content[].profileImage").description("Register의 프로필 이미지")
                )
            ));
    }
}