package com.pawith.todopresentation;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.response.ManageRegisterInfoResponse;
import com.pawith.todoapplication.dto.response.ManageRegisterListResponse;
import com.pawith.todoapplication.dto.response.RegisterListResponse;
import com.pawith.todoapplication.dto.response.RegisterSimpleInfoResponse;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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

    private static final String REGISTER_REQUEST_URL = "/register";
    private static final String Authrotity = "EXECUTIVE";

    @Test
    @DisplayName("TodoTeamId로 TodoTeam을 삭제하는 테스트")
    void unregisterTodoTeam() throws Exception {
        // given
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        MockHttpServletRequestBuilder request = RestDocumentationRequestBuilders.delete(REGISTER_REQUEST_URL + "/{todoTeamId}", todoTeamId)
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
        MockHttpServletRequestBuilder request = post(REGISTER_REQUEST_URL)
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
                requestParameters(
                    parameterWithName("todoTeamCode").description("TodoTeam의 코드")
                )
            ));
    }

    @Test
    @DisplayName("TodoTeam의 등록된 Register들을 조회하는 테스트")
    void getRegisters() throws Exception {
        //given
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final List<RegisterSimpleInfoResponse> registerSimpleInfoResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMe(RegisterSimpleInfoResponse.class, 10);
        final RegisterListResponse registerListResponse = new RegisterListResponse(registerSimpleInfoResponses);
        given(registersGetUseCase.getRegisters(todoTeamId)).willReturn(registerListResponse);
        MockHttpServletRequestBuilder request = get(REGISTER_REQUEST_URL + "/list?teamId={teamId}", todoTeamId)
            .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                requestParameters(
                    parameterWithName("teamId").description("조회하는 TodoTeamId")
                ),
                responseFields(
                    fieldWithPath("registers[].registerId").description("TodoTeam에 등록된 사용자 registerId"),
                    fieldWithPath("registers[].registerName").description("TodoTeam에 등록된 사용자 이메일")
                )
            ));

    }


    @Test
    @DisplayName("TodoTeam의 등록된 Register들을 조회하는 테스트 (관리자 페이지에서 사용)")
    void getManageRegisters() throws Exception {
        //given
        final Long todoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final List<ManageRegisterInfoResponse> manageRegisterInfoResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMe(ManageRegisterInfoResponse.class, 10);
        final ManageRegisterListResponse manageRegisterListResponse = new ManageRegisterListResponse(manageRegisterInfoResponses);
        given(registersGetUseCase.getManageRegisters(todoTeamId)).willReturn(manageRegisterListResponse);
        MockHttpServletRequestBuilder request = get(REGISTER_REQUEST_URL + "/manage/list?teamId={teamId}", todoTeamId)
                .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
                .andDo(resultHandler.document(
                        requestHeaders(
                                headerWithName("Authorization").description("access 토큰")
                        ),
                        requestParameters(
                                parameterWithName("teamId").description("조회하는 TodoTeamId")
                        ),
                        responseFields(
                                fieldWithPath("registers[].registerId").description("TodoTeam에 등록된 사용자 registerId"),
                                fieldWithPath("registers[].authority").description("TodoTeam에 등록된 사용자 권한"),
                                fieldWithPath("registers[].registerName").description("TodoTeam에 등록된 사용자 이름"),
                                fieldWithPath("registers[].registerEmail").description("TodoTeam에 등록된 사용자 이메일")
                        )
                ));

    }

    @Test
    @DisplayName("Register의 권한을 변경하는 테스트")
    void changeAuthority() throws Exception {
        //given
        final Long registerId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final String authority = Authrotity;
        MockHttpServletRequestBuilder request = post(REGISTER_REQUEST_URL + "/{registerId}", registerId)
            .queryParam("authority", authority)
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
                    parameterWithName("registerId").description("변경할 register의 Id")
                ),
                requestParameters(
                    parameterWithName("authority").description("변경할 register의 권한")
                )
            ));
    }
}