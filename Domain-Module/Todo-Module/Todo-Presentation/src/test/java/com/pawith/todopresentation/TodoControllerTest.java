package com.pawith.todopresentation;

import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.slice.SliceResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.request.ScheduledDateChangeRequest;
import com.pawith.todoapplication.dto.request.TodoCreateRequest;
import com.pawith.todoapplication.dto.request.TodoDescriptionChangeRequest;
import com.pawith.todoapplication.dto.response.*;
import com.pawith.todoapplication.service.*;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(TodoController.class)
@DisplayName("TodoController 테스트")
public class TodoControllerTest extends BaseRestDocsTest {
    @MockBean
    private TodoRateGetUseCase todoRateGetUseCase;
    @MockBean
    private TodoGetUseCase todoGetUseCase;
    @MockBean
    private TodoCreateUseCase todoCreateUseCase;
    @MockBean
    private TodoChangeUseCase todoChangeUseCase;

    private static final String TODO_REQUEST_URL = "/teams";

    @Test
    @DisplayName("Todo 달성률을 가져오는 테스트")
    void getTodoProgress() throws Exception{
        //given
        final TodoProgressResponse testTodoProgress = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(TodoProgressResponse.class);
        final Long testTeamId = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Long.class);
        given(todoRateGetUseCase.getTodoProgress(testTeamId)).willReturn(testTodoProgress);
        MockHttpServletRequestBuilder request = get(TODO_REQUEST_URL + "/{todoTeamId}/todos/progress", testTeamId)
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
                                fieldWithPath("progress").description("달성률")
                        )
                ));
    }

    @Test
    @DisplayName("할당받은 Todo 조회 API 테스트")
    void getTodos() throws Exception{
        //given
        final PageRequest pageRequest = PageRequest.of(0, 6);
        final Long testTeamId = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Long.class);
        final List<TodoInfoResponse> todoRespons = FixtureMonkeyUtils.getConstructBasedFixtureMonkey()
                .giveMeBuilder(TodoInfoResponse.class)
                .set("todoId", Arbitraries.longs().greaterOrEqual(1L))
                .set("task", Arbitraries.strings().withCharRange('a', 'z').ofMinLength(5).ofMaxLength(10))
                .set("status", FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder("COMPLETE"))
                .sampleList(pageRequest.getPageSize());
        final SliceImpl<TodoInfoResponse> slice = new SliceImpl(todoRespons, pageRequest, true);
        given(todoGetUseCase.getTodoListByTodoTeamId(any(), any())).willReturn(SliceResponse.from(slice));
        MockHttpServletRequestBuilder request = get(TODO_REQUEST_URL + "/{todoTeamId}/todos", testTeamId)
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
                                parameterWithName("todoTeamId").description("TodoTeam의 Id")
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

    @Test
    @DisplayName("todo 등록 API 테스트")
    void postTodo() throws Exception {
        //given
        final TodoCreateRequest todoCreateRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(TodoCreateRequest.class);
        MockHttpServletRequestBuilder request = post(TODO_REQUEST_URL+"/todos")
            .content(objectMapper.writeValueAsString(todoCreateRequest))
            .contentType(MediaType.APPLICATION_JSON)
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
                    fieldWithPath("categoryId").description("todo 등록 카테고리 id"),
                    fieldWithPath("description").description("todo 설명"),
                    fieldWithPath("scheduledDate").description("todo 완료 기한 날짜"),
                    fieldWithPath("registerIds[]").description("todo를 할당할 사용자 registerId들")
                )
            ));
    }

    @Test
    @DisplayName("투두 리스트 조회 API 테스트")
    void getTodoList() throws Exception{
        //given
        final Long testCategoryId = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Long.class);
        final LocalDate testMoveDate = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(LocalDate.class);
        final List<AssignUserInfoResponse> assignUserInfoResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMe(AssignUserInfoResponse.class, 2);
        final List<CategorySubTodoResponse> categorySubTodoResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey()
            .giveMeBuilder(CategorySubTodoResponse.class)
            .set("todoId", Arbitraries.longs().greaterOrEqual(1L))
            .set("task", Arbitraries.strings().withCharRange('a', 'z').ofMinLength(5).ofMaxLength(10))
            .set("status", FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder("COMPLETE"))
            .set("assignNames", assignUserInfoResponses)
            .sampleList(2);
        final CategorySubTodoListResponse categorySubTodoListResponse = new CategorySubTodoListResponse(categorySubTodoResponses);
        given(todoGetUseCase.getTodoListByCategoryId(any(), any())).willReturn(categorySubTodoListResponse);
        MockHttpServletRequestBuilder request = get(TODO_REQUEST_URL + "/category/{categoryId}/todos", testCategoryId)
                .queryParam("moveDate", testMoveDate.toString())
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
                                parameterWithName("categoryId").description("Category의 Id")
                        ),
                        requestParameters(
                                parameterWithName("moveDate").description("달력에서 이동하는 날짜(LocalDate)")
                        ),
                        responseFields(
                                fieldWithPath("todos[].todoId").description("투두 항목 Id"),
                                fieldWithPath("todos[].task").description("투두 항목 이름"),
                                fieldWithPath("todos[].status").description("투두 항목 상태(완료, 미완료)"),
                                fieldWithPath("todos[].assignNames[].assigneeId").description("할당받은 사용자의 ID"),
                                fieldWithPath("todos[].assignNames[].assigneeName").description("할당받은 사용자의 이름")
                        )
                ));
    }


    @Test
    @DisplayName("주차 달성률 비교 API 테스트")
    void getWeekProgressCompare() throws Exception {
        //given
        final Long testTeamId = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Long.class);
        given(todoRateGetUseCase.getWeekProgressCompare(testTeamId)).willReturn(new TodoProgressRateCompareResponse(true, false));
        MockHttpServletRequestBuilder request = get(TODO_REQUEST_URL + "/{todoTeamId}/todos/progress/compare", testTeamId)
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
                                fieldWithPath("compareWithLastWeek").description("지난주 달성률과 이번주 달성률 비교. HIGER : 지난주보다 높음, LOWER : 지난주보다 낮음, SAME : 지난주와 같음")
                        )
                ));
    }

    @Test
    @DisplayName("투두 날짜 변경 API 테스트")
    void changeScheduledDate() throws Exception {
        //given
        final Long testTodoId = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Long.class);
        final ScheduledDateChangeRequest scheduledDateChangeRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(ScheduledDateChangeRequest.class);
        MockHttpServletRequestBuilder request = put(TODO_REQUEST_URL + "/todos/{todoId}/date", testTodoId)
                .contentType("application/json")
                .header("Authorization", "Bearer accessToken")
                .content(objectMapper.writeValueAsString(scheduledDateChangeRequest));
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
                .andDo(resultHandler.document(
                        requestHeaders(
                                headerWithName("Authorization").description("access 토큰")
                        ),
                        pathParameters(
                                parameterWithName("todoId").description("투두 항목 Id")
                        ),
                        requestFields(
                                fieldWithPath("scheduledDate").description("변경할 날짜")
                        )
                ));
    }

    @Test
    @DisplayName("투두 이름 변경 API 테스트")
    void changeTodoName() throws Exception {
        //given
        final Long testTodoId = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(Long.class);
        final TodoDescriptionChangeRequest todoDescriptionChangeRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(TodoDescriptionChangeRequest.class);
        MockHttpServletRequestBuilder request = put(TODO_REQUEST_URL + "/todos/{todoId}/description", testTodoId)
                .contentType("application/json")
                .header("Authorization", "Bearer accessToken")
                .content(objectMapper.writeValueAsString(todoDescriptionChangeRequest));
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
                .andDo(resultHandler.document(
                        requestHeaders(
                                headerWithName("Authorization").description("access 토큰")
                        ),
                        pathParameters(
                                parameterWithName("todoId").description("투두 항목 Id")
                        ),
                        requestFields(
                                fieldWithPath("description").description("변경할 이름")
                        )
                ));
    }

}
