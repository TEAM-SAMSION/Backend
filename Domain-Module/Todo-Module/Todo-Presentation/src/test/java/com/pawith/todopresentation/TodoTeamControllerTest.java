package com.pawith.todopresentation;

import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.commonmodule.response.SliceResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.response.*;
import com.pawith.todoapplication.service.TodoTeamChangeUseCase;
import com.pawith.todoapplication.service.TodoTeamCreateUseCase;
import com.pawith.todoapplication.service.TodoTeamGetUseCase;
import com.pawith.todoapplication.service.TodoTeamRandomCodeGetUseCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(TodoTeamController.class)
@DisplayName("TodoTeamController 테스트")
class TodoTeamControllerTest extends BaseRestDocsTest {

    private static final String TODO_TEAM_REQUEST_URL = "/teams";
    private static final String TODO_TEAM_CREATE_INFO = "{\n" +
        "    \"teamName\" : \"test\",\n" +
        "    \"description\" : \"test\",\n" +
        "    \"randomCode\" : \"randomCode\",\n" +
        "    \"petRegisters\" : [ {\n" +
        "        \"name\" : \"이름이 뭐에요~\",\n" +
        "        \"age\" : 2,\n" +
        "        \"description\" : \"귀엽습니다\",\n" +
        "        \"genus\" : \"과\",\n" +
        "        \"species\" : \"종\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"name\" : \"이름이 뭐에요~\",\n" +
        "        \"age\" : 2,\n" +
        "        \"description\" : \"귀엽습니다\",\n" +
        "        \"genus\" : \"과\",\n" +
        "        \"species\" : \"종\"\n" +
        "    }\n" +
        "\n" +
        "    ]\n" +
        "}";
    private static final String TODO_TEAM_UPDATE_INFO = "{\n" +
        "    \"teamName\" : \"test\",\n" +
        "    \"description\" : \"test\"\n" +
        "}";
    @MockBean
    private TodoTeamGetUseCase todoTeamGetUseCase;
    @MockBean
    private TodoTeamRandomCodeGetUseCase todoTeamRandomCodeGetUseCase;
    @MockBean
    private TodoTeamCreateUseCase todoTeamCreateUseCase;
    @MockBean
    private TodoTeamChangeUseCase todoTeamChangeUseCase;

    @Test
    @DisplayName("TodoTeam 목록을 가져오는 테스트")
    void getTodoTeams() throws Exception {
        //given
        final PageRequest pageRequest = PageRequest.of(0, 10);
        final List<TodoTeamInfoResponse> myPageTodoTeamRespons = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeBuilder(TodoTeamInfoResponse.class)
            .sampleList(pageRequest.getPageSize());
        myPageTodoTeamRespons.sort(((o1, o2) -> (int) (o2.getTeamId() - o1.getTeamId())));
        final SliceImpl<TodoTeamInfoResponse> slice = new SliceImpl(myPageTodoTeamRespons, pageRequest, true);
        given(todoTeamGetUseCase.getTodoTeams(any())).willReturn(SliceResponse.from(slice));
        MockHttpServletRequestBuilder request = get(TODO_TEAM_REQUEST_URL)
            .queryParam("page", String.valueOf(pageRequest.getPageNumber()))
            .queryParam("size", String.valueOf(pageRequest.getPageSize()))
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
                    parameterWithName("page").description("요청 페이지"),
                    parameterWithName("size").description("요청 사이즈")
                ),
                responseFields(
                    fieldWithPath("content[].teamId").description("TodoTeam의 Id"),
                    fieldWithPath("content[].teamName").description("TodoTeam의 이름"),
                    fieldWithPath("content[].teamProfileImageUrl").description("TodoTeam의 이미지"),
                    fieldWithPath("content[].authority").description("TodoTeam의 권한"),
                    fieldWithPath("content[].registerPeriod").description("TodoTeam 가입 후 기간"),
                    fieldWithPath("page").description("요청 페이지"),
                    fieldWithPath("size").description("요청 사이즈"),
                    fieldWithPath("hasNext").description("다음 데이터 존재 여부")
                )
            ));
    }

    @Test
    @DisplayName("TodoTeam 랜덤 코드를 가져오는 테스트")
    void getTodoTeamRandomCode() throws Exception {
        //given
        given(todoTeamRandomCodeGetUseCase.generateRandomCode()).willReturn(new TodoTeamRandomCodeResponse("randomCode"));
        MockHttpServletRequestBuilder request = get(TODO_TEAM_REQUEST_URL + "/codes/random")
            .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                responseFields(
                    fieldWithPath("randomCode").description("랜덤 코드")
                )
            ));
    }

    @Test
    @DisplayName("teamId를 이용해서 TodoTeam의 code를 조회 테스트")
    void getTodoTeamCode() throws Exception {
        //given
        final Long teamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        given(todoTeamGetUseCase.getTodoTeamCode(teamId)).willReturn(new TodoTeamRandomCodeResponse("randomCode"));
        MockHttpServletRequestBuilder request = get(TODO_TEAM_REQUEST_URL + "/{todoTeamId}/codes", teamId)
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
                                fieldWithPath("randomCode").description("TodoTeam의 코드")
                        )
                ));
    }

    @Test
    @DisplayName("TodoTeam 생성 테스트")
    void postTodoTeam() throws Exception {
        //given
        final MockMultipartFile mockMultipartFileTeam = new MockMultipartFile("teamImageFile", "teamImageFile", "image/jpeg", "image".getBytes());
        final MockMultipartFile mockMultipartFilePet1 = new MockMultipartFile("petimageFiles", "petimageFiles", "image/jpeg", "image".getBytes());
        final MockMultipartFile mockMultipartFilePet2 = new MockMultipartFile("petimageFiles", "petimageFiles", "image/jpeg", "image".getBytes());
        final MockMultipartFile todoTeamCreateInfo = new MockMultipartFile("todoTeamCreateInfo", "", MediaType.APPLICATION_JSON_VALUE, TODO_TEAM_CREATE_INFO.getBytes());
        MockHttpServletRequestBuilder request = multipart(TODO_TEAM_REQUEST_URL)
            .file(mockMultipartFileTeam)
            .file(mockMultipartFilePet1)
            .file(mockMultipartFilePet2)
            .file(todoTeamCreateInfo)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                requestPartFields("todoTeamCreateInfo",
                    fieldWithPath("teamName").description("TodoTeam 이름"),
                    fieldWithPath("randomCode").description("TodoTeam 랜덤 코드"),
                    fieldWithPath("description").description("TodoTeam 한줄설명"),
                    fieldWithPath("petRegisters[].name").description("Pet 이름"),
                    fieldWithPath("petRegisters[].age").description("Pet 나이"),
                    fieldWithPath("petRegisters[].description").description("Pet 설명"),
                    fieldWithPath("petRegisters[].genus").description("Pet 종"),
                    fieldWithPath("petRegisters[].species").description("Pet 품종")
                )
            ));
    }

    @Test
    @DisplayName("가입한 팀 조회 API 테스트")
    void getTodoTeamName() throws Exception {
        //given
        final List<TodoTeamNameResponse> todoTeamNameResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMe(TodoTeamNameResponse.class, 5);
        given(todoTeamGetUseCase.getTodoTeamName()).willReturn(ListResponse.from(todoTeamNameResponses));
        MockHttpServletRequestBuilder request = get(TODO_TEAM_REQUEST_URL + "/name")
            .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                responseFields(
                    fieldWithPath("content[].teamId").description("TodoTeam의 Id"),
                    fieldWithPath("content[].teamName").description("TodoTeam의 이름"),
                    fieldWithPath("content[].authority").description("TodoTeam의 권한")
                )
            ));
    }


    @Test
    @DisplayName("code를 이용해서 TodoTeam을 조회 테스트")
    void getTodoTeamByCode() throws Exception {
        //given
        final String code = "e7ce90da";
        final TodoTeamSearchInfoResponse todoTeamSearchInfoResponse = FixtureMonkeyUtils.getConstructBasedFixtureMonkey()
            .giveMeBuilder(TodoTeamSearchInfoResponse.class)
            .set("code", code)
            .sample();
        given(todoTeamGetUseCase.searchTodoTeamByCode(code)).willReturn(todoTeamSearchInfoResponse);
        MockHttpServletRequestBuilder request = get(TODO_TEAM_REQUEST_URL+"/codes/{code}", code)
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
                    parameterWithName("code").description("TodoTeam의 코드")
                ),
                responseFields(
                    fieldWithPath("code").description("TodoTeam의 코드"),
                    fieldWithPath("teamName").description("TodoTeam의 이름"),
                    fieldWithPath("presidentName").description("TodoTeam의 대표자 이름"),
                    fieldWithPath("registerCount").description("TodoTeam의 가입자 수"),
                    fieldWithPath("description").description("TodoTeam의 설명"),
                    fieldWithPath("teamImageUrl").description("TodoTeam의 이미지")
                )
            ));
    }

    @Test
    @DisplayName("서비스 탈퇴 시 가입한 팀 조회 API 테스트")
    void getWithdrawTodoTeam() throws Exception {
        //given
        final List<WithdrawTodoTeamResponse> withdrawTodoTeamResponses =
            FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(WithdrawTodoTeamResponse.class, 5);
        given(todoTeamGetUseCase.getWithdrawTodoTeam()).willReturn(ListResponse.from(withdrawTodoTeamResponses));
        MockHttpServletRequestBuilder request = get(TODO_TEAM_REQUEST_URL + "/withdraw")
                .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
                .andDo(resultHandler.document(
                        requestHeaders(
                                headerWithName("Authorization").description("access 토큰")
                        ),
                        responseFields(
                                fieldWithPath("content[].teamProfileImage").description("TodoTeam의 이미지"),
                                fieldWithPath("content[].teamName").description("TodoTeam의 이름")
                        )
                ));
    }

    @Test
    @DisplayName("TodoTeam 상세 정보 조회 API 테스트")
    void getTodoTeamInfo() throws Exception {
        //given
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final TodoTeamInfoDetailResponse todoTeamInfoDetailResponse = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMeOne(TodoTeamInfoDetailResponse.class);
        given(todoTeamGetUseCase.getTodoTeamInfo(todoTeamId)).willReturn(todoTeamInfoDetailResponse);
        MockHttpServletRequestBuilder request = get(TODO_TEAM_REQUEST_URL+"/{todoTeamId}", todoTeamId)
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
                    fieldWithPath("todoTeamCode").description("TodoTeam의 코드"),
                    fieldWithPath("teamDescription").description("TodoTeam의 설명"),
                    fieldWithPath("teamMemberCount").description("TodoTeam의 가입자 수"),
                    fieldWithPath("teamPetCount").description("TodoTeam의 펫 수")
                )
            ));
    }

    @Test
    @DisplayName("TodoTeam 정보 수정 API 테스트")
    void putTodoTeamInfo() throws Exception {
        //given
        final Long todoTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final MockMultipartFile mockMultipartFileTeam = new MockMultipartFile("teamImageFile", "teamImageFile", "image/jpeg", "image".getBytes());
        final MockMultipartFile todoTeamInfoChangeRequest = new MockMultipartFile("todoTeamUpdateInfo", "", MediaType.APPLICATION_JSON_VALUE, TODO_TEAM_UPDATE_INFO.getBytes());
        //when
        MockHttpServletRequestBuilder request = multipart(TODO_TEAM_REQUEST_URL+"/{todoTeamId}", todoTeamId)
            .file(mockMultipartFileTeam)
            .file(todoTeamInfoChangeRequest)
            .accept(MediaType.APPLICATION_JSON)
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
                requestPartFields("todoTeamUpdateInfo",
                    fieldWithPath("teamName").description("TodoTeam 이름"),
                    fieldWithPath("description").description("TodoTeam 한줄설명")
                )
            ));
    }
}