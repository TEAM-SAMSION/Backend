package com.pawith.authpresentation;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.pawith.authapplication.service.OAuthUseCase;
import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.authapplication.dto.Provider;
import com.pawith.authpresentation.common.FilterConfig;
import com.pawith.commonmodule.BaseRestDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OAuthController.class)
@Import(FilterConfig.class)
@DisplayName("OAuthController 테스트")
class OAuthControllerTest extends BaseRestDocsTest {

    private static final String OAUTH_URL = "/oauth/{provider}";
    private static final String OAUTH_REQUEST_ACCESS_TOKEN_PARAM_NAME = "accessToken";

    @MockBean
    OAuthUseCase oAuthUseCase;

    @Test
    @DisplayName("OAuth 로그인")
    void oAuthLogin() throws Exception {
        //given
        final Provider testProvider = FixtureMonkey.create().giveMeOne(Provider.class);
        final String OAUTH_ACCESS_TOKEN = FixtureMonkey.create().giveMeOne(String.class);
        final OAuthResponse testOAuthResponse = getFixtureMonkey().giveMeOne(OAuthResponse.class);
        MockHttpServletRequestBuilder request = get(OAUTH_URL, testProvider)
            .param(OAUTH_REQUEST_ACCESS_TOKEN_PARAM_NAME, OAUTH_ACCESS_TOKEN);
        given(oAuthUseCase.oAuthLogin(testProvider, OAUTH_ACCESS_TOKEN)).willReturn(testOAuthResponse);
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(print())
            .andDo(resultHandler.document(
                requestParameters(
                    parameterWithName(OAUTH_REQUEST_ACCESS_TOKEN_PARAM_NAME).description("OAuth 접근 토큰")
                ),
                pathParameters(
                    parameterWithName("provider").description("OAuth 제공자")
                ),
                responseFields(
                    fieldWithPath("accessToken").description("access 토큰"),
                    fieldWithPath("refreshToken").description("refresh 토큰")
                )
            ));

    }

    private FixtureMonkey getFixtureMonkey() {
        return FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build();
    }
}