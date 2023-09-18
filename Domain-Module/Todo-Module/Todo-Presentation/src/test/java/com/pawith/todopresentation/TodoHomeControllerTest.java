package com.pawith.todopresentation;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.todoapplication.dto.response.TodoProgressResponse;
import com.pawith.todoapplication.dto.response.TodoTeamNameSimpleResponse;
import com.pawith.todoapplication.service.TodoRateGetUseCase;
import com.pawith.todoapplication.service.TodoTeamGetUseCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(TodoHomeController.class)
@DisplayName("RegisterController 테스트")
public class TodoHomeControllerTest extends BaseRestDocsTest {

    @MockBean
    private TodoRateGetUseCase todoRateGetUseCase;

    @MockBean
    private TodoTeamGetUseCase todoTeamGetUseCase;

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
                                parameterWithName("teamId").description("TodoTeam의 Id. 홈 화면 처음 들어올때는 null 상태로 보내야함")
                        ),
                        responseFields(
                                fieldWithPath("progress").description("달성률")
                        )
                ));
    }

    @Test
    @DisplayName("가입한 팀 조회 API 테스트")
    void getRecentTodoTeamName() throws Exception{
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

    private FixtureMonkey getFixtureMonkey() {
        return FixtureMonkey.builder()
                .defaultNotNull(true)
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
    }
}
