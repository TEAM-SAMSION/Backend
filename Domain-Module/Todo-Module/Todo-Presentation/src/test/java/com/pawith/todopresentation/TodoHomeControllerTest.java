package com.pawith.todopresentation;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.response.TodoHomeResponse;
import com.pawith.todoapplication.dto.response.TodoProgressResponse;
import com.pawith.todoapplication.dto.response.TodoTeamNameSimpleResponse;
import com.pawith.todoapplication.service.TodoGetUseCase;
import com.pawith.todoapplication.service.TodoRateGetUseCase;
import com.pawith.todoapplication.service.TodoTeamGetUseCase;
import lombok.extern.slf4j.Slf4j;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(TodoHomeController.class)
@DisplayName("RegisterController 테스트")
public class TodoHomeControllerTest extends BaseRestDocsTest {

    @MockBean
    private TodoRateGetUseCase todoRateGetUseCase;

    @MockBean
    private TodoTeamGetUseCase todoTeamGetUseCase;

    @MockBean
    private TodoGetUseCase todoGetUseCase;

    private static final String TODO_HOME_REQUEST_URL = "/todo/home";

    @Test
    @DisplayName("Todo 달성률을 가져오는 테스트")
    void getTodoProgress() throws Exception{
        //given
        final TodoProgressResponse testTodoProgress = getFixtureMonkey().giveMeOne(TodoProgressResponse.class);
        final Long testTeamId = getFixtureMonkey().giveMeOne(Long.class);
        given(todoRateGetUseCase.getTodoProgress(testTeamId)).willReturn(testTodoProgress);
        MockHttpServletRequestBuilder request = get(TODO_HOME_REQUEST_URL + "/progress/{teamId}", testTeamId)
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
                                parameterWithName("teamId").description("TodoTeam의 Id")
                        ),
                        responseFields(
                                fieldWithPath("progress").description("달성률")
                        )
                ));
    }

    @Test
    @DisplayName("가입한 팀 조회 API 테스트")
    void getTodoTeamName() throws Exception{
        //given
        final List<TodoTeamNameSimpleResponse> todoTeamNameSimpleResponses = getFixtureMonkey().giveMe(TodoTeamNameSimpleResponse.class,5);
        given(todoTeamGetUseCase.getTodoTeamName()).willReturn(todoTeamNameSimpleResponses);
        MockHttpServletRequestBuilder request = get(TODO_HOME_REQUEST_URL)
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
                                fieldWithPath("[].teamId").description("TodoTeam의 Id"),
                                fieldWithPath("[].teamName").description("TodoTeam의 이름")
                        )
                ));
    }

    @Test
    @DisplayName("할당받은 Todo 조회 API 테스트")
    void getTodos() throws Exception{
        //given
        final PageRequest pageRequest = PageRequest.of(0, 6);
        final Long testTeamId = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Long.class);
        final List<TodoHomeResponse> todoHomeResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey()
                .giveMeBuilder(TodoHomeResponse.class)
                .set("todoId", Arbitraries.longs().greaterOrEqual(1L))
                .set("task", Arbitraries.strings().withCharRange('a', 'z').ofMinLength(5).ofMaxLength(10))
                .set("status", FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder("COMPLETE"))
                .sampleList(pageRequest.getPageSize());
        final SliceImpl<TodoHomeResponse> slice = new SliceImpl(todoHomeResponses, pageRequest, true);
        given(todoGetUseCase.getTodos(any(), any())).willReturn(SliceResponse.from(slice));
        MockHttpServletRequestBuilder request = get(TODO_HOME_REQUEST_URL + "/list/{teamId}", testTeamId)
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
                        pathParameters(
                                parameterWithName("teamId").description("TodoTeam의 Id")
                        ),
                        requestParameters(
                                parameterWithName("page").description("요청 페이지"),
                                parameterWithName("size").description("요청 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("content[].todoId").description("투두 항목 Id"),
                                fieldWithPath("content[].task").description("투두 항목 이름"),
                                fieldWithPath("content[].status").description("투두 항목 상태(완료, 미완료)"),
                                fieldWithPath("page").description("요청 페이지"),
                                fieldWithPath("size").description("요청 사이즈"),
                                fieldWithPath("hasNext").description("다음 데이터 존재 여부")
                        )
                ));
    }

    private FixtureMonkey getFixtureMonkey() {
        return FixtureMonkey.builder()
                .defaultNotNull(true)
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
    }
}
