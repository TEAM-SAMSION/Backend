package com.pawith.todopresentation;

import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.request.CategoryCreateRequest;
import com.pawith.todoapplication.dto.request.CategoryNameChageRequest;
import com.pawith.todoapplication.dto.response.CategoryInfoResponse;
import com.pawith.todoapplication.dto.response.CategoryManageInfoResponse;
import com.pawith.todoapplication.service.CategoryChangeUseCase;
import com.pawith.todoapplication.service.CategoryCreateUseCase;
import com.pawith.todoapplication.service.CategoryDeleteUseCase;
import com.pawith.todoapplication.service.CategoryGetUseCase;
import java.time.LocalDate;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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
    @MockBean
    private CategoryCreateUseCase categoryCreateUseCase;


    private static final String CATEGORY_REQUEST_URL = "/teams";

    @Test
    @DisplayName("카테고리 조회 테스트")
    public void getCategoryList() throws Exception {
        // given
        final Long testTeamId = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Long.class);
        final List<CategoryInfoResponse> categoryListResponses = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(CategoryInfoResponse.class, 2);
        final LocalDate mockMoveDate = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(LocalDate.class);
        given(categoryGetUseCase.getCategoryList(testTeamId, mockMoveDate)).willReturn(ListResponse.from(categoryListResponses));
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
                                fieldWithPath("content[].categoryId").description("Category의 Id"),
                                fieldWithPath("content[].categoryName").description("Category의 이름")
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

    @Test
    @DisplayName("카테고리 생성 테스트")
    public void postCategory() throws Exception {
        // given
        final Long testTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final CategoryCreateRequest categoryCreateRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(CategoryCreateRequest.class);
        MockHttpServletRequestBuilder request = post(CATEGORY_REQUEST_URL + "/{teamId}/category", testTeamId)
                .header("Authorization", "Bearer accessToken")
                .content(objectMapper.writeValueAsString(categoryCreateRequest))
                .contentType("application/json");
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
                        requestFields(
                                fieldWithPath("categoryName").description("생성할 Category의 이름")
                        )
                ));
    }

    @Test
    @DisplayName("카테고리 이름 변경 테스트")
    public void putCategoryName() throws Exception {
        // given
        final Long testCategoryId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final CategoryNameChageRequest categoryNameChageRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(CategoryNameChageRequest.class);
        MockHttpServletRequestBuilder request = put(CATEGORY_REQUEST_URL + "/category/{categoryId}/name", testCategoryId)
                .header("Authorization", "Bearer accessToken")
                .content(objectMapper.writeValueAsString(categoryNameChageRequest))
                .contentType("application/json");
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
                        ),
                        requestFields(
                                fieldWithPath("categoryName").description("변경할 Category의 이름")
                        )
                ));
    }

    @Test
    @DisplayName("카테고리 조회 테스트(관리자 페이지)")
    public void getCategoryListForManage() throws Exception {
        // given
        final Long testTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final List<CategoryManageInfoResponse> categoryListResponses = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(CategoryManageInfoResponse.class, 2);
        given(categoryGetUseCase.getManageCategoryList(testTeamId)).willReturn(ListResponse.from(categoryListResponses));
        MockHttpServletRequestBuilder request = get(CATEGORY_REQUEST_URL + "/{teamId}/category/manage", testTeamId)
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
                                fieldWithPath("content[].categoryId").description("Category의 Id"),
                                fieldWithPath("content[].categoryName").description("Category의 이름"),
                                fieldWithPath("content[].categoryStatus").description("Category의 상태"),
                                fieldWithPath("content[].categoryStatus").description("Category의 상태(ON, OFF)")
                        )
                ));
    }

}
