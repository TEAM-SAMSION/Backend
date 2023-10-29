package com.pawith.todopresentation;

import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.response.CategoryInfoResponse;
import com.pawith.todoapplication.dto.response.CategoryInfoListResponse;
import com.pawith.todoapplication.service.CategoryChangeUseCase;
import com.pawith.todoapplication.service.CategoryDeleteUseCase;
import com.pawith.todoapplication.service.CategoryGetUseCase;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(CategoryController.class)
@DisplayName("CategoryController 테스트")
public class CategoryControllerTest extends BaseRestDocsTest {

    @MockBean
    private CategoryGetUseCase categoryGetUseCase;
    @MockBean
    private CategoryChangeUseCase categoryChangeUseCase;
    @MockBean
    private CategoryDeleteUseCase categoryDeleteUseCase;
    private static final String CATEGORY_REQUEST_URL = "/teams";

    @Test
    @DisplayName("카테고리 조회 테스트")
    public void getCategoryList() throws Exception {
        // given
        final Long testTeamId = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Long.class);
        final List<CategoryInfoResponse> categoryListResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMe(CategoryInfoResponse.class, 2);
        final CategoryInfoListResponse categoryInfoListResponse = new CategoryInfoListResponse(categoryListResponses);
        given(categoryGetUseCase.getCategoryList(testTeamId)).willReturn(categoryInfoListResponse);
        MockHttpServletRequestBuilder request = get(CATEGORY_REQUEST_URL + "/{teamId}/category", testTeamId)
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
                                parameterWithName("teamId").description("TodoTeam의 Id")
                        ),
                        responseFields(
                                fieldWithPath("categories[].categoryId").description("Category의 Id"),
                                fieldWithPath("categories[].categoryName").description("Category의 이름")
                        )
                ));
    }

    @Test
    @DisplayName("카테고리 상태 변경 테스트")
    public void putCategoryStatus() throws Exception {
        // given
        final Long testCategoryId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        MockHttpServletRequestBuilder request = put(CATEGORY_REQUEST_URL + "/category/{categoryId}", testCategoryId)
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
                                parameterWithName("categoryId").description("Category의 Id")
                        )
                ));
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    public void deleteCategory() throws Exception {
        // given
        final Long testCategoryId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        MockHttpServletRequestBuilder request = delete(CATEGORY_REQUEST_URL + "/category/{categoryId}", testCategoryId)
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
                                parameterWithName("categoryId").description("Category의 Id")
                        )
                ));
    }

}
