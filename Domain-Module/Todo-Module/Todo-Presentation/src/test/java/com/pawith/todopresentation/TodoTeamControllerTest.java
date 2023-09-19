package com.pawith.todopresentation;

import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.request.PetRegisterRequest;
import com.pawith.todoapplication.dto.request.TodoTeamCreateRequest;
import com.pawith.todoapplication.dto.response.TodoTeamRandomCodeResponse;
import com.pawith.todoapplication.dto.response.TodoTeamSimpleResponse;
import com.pawith.todoapplication.service.TodoTeamCreateUseCase;
import com.pawith.todoapplication.service.TodoTeamGetUseCase;
import com.pawith.todoapplication.service.TodoTeamRandomCodeGetUseCase;
import lombok.extern.slf4j.Slf4j;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(TodoTeamController.class)
@DisplayName("TodoTeamController 테스트")
class TodoTeamControllerTest extends BaseRestDocsTest {

    @MockBean
    private TodoTeamGetUseCase todoTeamGetUseCase;
    @MockBean
    private TodoTeamRandomCodeGetUseCase todoTeamRandomCodeGetUseCase;
    @MockBean
    private TodoTeamCreateUseCase todoTeamCreateUseCase;

    private static final String TODO_TEAM_REQUEST_URL = "/todo/team";

    @Test
    @DisplayName("TodoTeam 목록을 가져오는 테스트")
    void getTodoTeams() throws Exception{
        //given
        final PageRequest pageRequest = PageRequest.of(0, 10);
        final List<TodoTeamSimpleResponse> todoTeamSimpleResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey()
            .giveMeBuilder(TodoTeamSimpleResponse.class)
            .set("teamId", Arbitraries.longs().greaterOrEqual(1L))
            .set("registerPeriod", Arbitraries.integers().greaterOrEqual(1))
            .set("teamName", Arbitraries.strings().withCharRange('a', 'z').ofMinLength(5).ofMaxLength(10))
            .sampleList(pageRequest.getPageSize());
        todoTeamSimpleResponses.sort(((o1, o2) -> (int) (o2.getTeamId() - o1.getTeamId())));
        final SliceImpl<TodoTeamSimpleResponse> slice = new SliceImpl(todoTeamSimpleResponses, pageRequest, true);
        given(todoTeamGetUseCase.getTodoTeams(any())).willReturn(SliceResponse.from(slice));
        MockHttpServletRequestBuilder request = get(TODO_TEAM_REQUEST_URL + "/list")
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
                requestParameters(
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
        MockHttpServletRequestBuilder request = get(TODO_TEAM_REQUEST_URL + "/code")
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
    @DisplayName("TodoTeam 생성 테스트")
    void postTodoTeam() throws Exception {
        //given
        final List<PetRegisterRequest> petRegisterRequests = FixtureMonkeyUtils.getConstructBasedFixtureMonkey()
            .giveMeBuilder(PetRegisterRequest.class)
            .set("name", Arbitraries.strings().withCharRange('a', 'z').ofMinLength(5).ofMaxLength(10))
            .set("age", Arbitraries.integers().between(1, 15))
            .set("imageUrl", "https://image/" + UUID.randomUUID().toString())
            .sampleList(4);
        final TodoTeamCreateRequest todoTeamCreateRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey()
            .giveMeBuilder(TodoTeamCreateRequest.class)
            .set("teamName", Arbitraries.strings().withCharRange('a', 'z').ofMinLength(5).ofMaxLength(10))
            .set("randomCode",UUID.randomUUID().toString().split("-")[0])
            .set("petRegisters", petRegisterRequests)
            .sample();

        MockHttpServletRequestBuilder request = post(TODO_TEAM_REQUEST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(todoTeamCreateRequest))
            .header("Authorization", "Bearer accessToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                requestFields(
                    fieldWithPath("teamName").description("팀 이름"),
                    fieldWithPath("randomCode").description("팀 코드"),
                    fieldWithPath("petRegisters[].name").description("펫 이름"),
                    fieldWithPath("petRegisters[].age").description("펫 나이"),
                    fieldWithPath("petRegisters[].description").description("펫 한 줄 설명"),
                    fieldWithPath("petRegisters[].imageUrl").description("펫 이미지")
                )
            ));
    }
}