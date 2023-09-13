package com.pawith.todopresentation;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.todoapplication.service.UnregisterUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegisterController.class)
@DisplayName("RegisterController 테스트")
class RegisterControllerTest extends BaseRestDocsTest {

    @MockBean
    private UnregisterUseCase unregisterUseCase;

    private static final String REGISTER_REQUEST_URL = "/register";

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

}