package com.pawith.authpresentation;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.authapplication.dto.TokenReissueResponse;
import com.pawith.authapplication.service.LogoutUseCase;
import com.pawith.authapplication.service.OAuthUseCase;
import com.pawith.authapplication.service.ReissueUseCase;
import com.pawith.authpresentation.common.FilterConfig;
import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.enums.Provider;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(OAuthController.class)
@Import(FilterConfig.class)
@DisplayName("OAuthController 테스트")
class OAuthControllerTest extends BaseRestDocsTest {

    private static final String OAUTH_URL = "/oauth/{provider}";
    private static final String OAUTH_REQUEST_ACCESS_TOKEN_PARAM_NAME = "accessToken";

    @MockBean
    private OAuthUseCase oAuthUseCase;
    @MockBean
    private LogoutUseCase logoutUseCase;
    @MockBean
    private ReissueUseCase reissueUseCase;

    @Test
    @DisplayName("OAuth 로그인")
    void oAuthLogin() throws Exception {
        //given
        final Provider testProvider = FixtureMonkey.create().giveMeOne(Provider.class);
        final String OAUTH_ACCESS_TOKEN = FixtureMonkey.create().giveMeOne(String.class);
        final OAuthResponse testOAuthResponse = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(OAuthResponse.class);
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

    @Test
    @DisplayName("로그아웃")
    void logout() throws Exception {
        //given
        MockHttpServletRequestBuilder request = delete("/logout")
            .header("Authorization","Bearer accessToken")
            .header("RefreshToken","Bearer refreshToken");
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(print())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰"),
                    headerWithName("RefreshToken").description("refresh 토큰")
                )
            ));
    }


    @Test
    @DisplayName("토큰 재발급")
    void reissue() throws Exception {
        //given
        final String refreshToken = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final String reissueAccessToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX+FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        final String reissueRefreshToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX+FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(String.class);
        given(reissueUseCase.reissue(AuthConsts.AUTHENTICATION_TYPE_PREFIX+refreshToken)).willReturn(new TokenReissueResponse(reissueAccessToken, reissueRefreshToken));
        MockHttpServletRequestBuilder request = post("/reissue")
            .header(AuthConsts.REFRESH_TOKEN_HEADER,AuthConsts.AUTHENTICATION_TYPE_PREFIX+refreshToken);
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(print())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName(AuthConsts.REFRESH_TOKEN_HEADER).description("refresh 토큰")
                ),
                responseFields(
                    fieldWithPath("accessToken").description("access 토큰"),
                    fieldWithPath("refreshToken").description("refresh 토큰")
                )
            ));
    }
}