package com.pawith.alarmmodule.controller;

import com.pawith.alarmmodule.service.AlarmService;
import com.pawith.alarmmodule.service.dto.response.AlarmExistenceResponse;
import com.pawith.alarmmodule.service.dto.response.AlarmInfoResponse;
import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.response.SliceResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest(AlarmController.class)
@DisplayName("AlarmController 테스트")
class AlarmControllerTest extends BaseRestDocsTest {

    private static final String BASE_URL = "/alarms";

    @MockBean
    private AlarmService alarmService;

    @Test
    @DisplayName("알람 존재 여부 확인")
    void getAlarmsExist() throws Exception {
        // given
        final AlarmExistenceResponse alarmExistenceResponse = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(AlarmExistenceResponse.class);
        given(alarmService.getAlarmsExist()).willReturn(alarmExistenceResponse);
        MockHttpServletRequestBuilder request = RestDocumentationRequestBuilders.get(BASE_URL + "/exist")
            .header("Authorization", "Bearer token");
        // when
        ResultActions result = mvc.perform(request);
        // then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                responseFields(
                    fieldWithPath("isExist").description("알람 존재 여부")
                )
            ));
    }


    @Test
    @DisplayName("알람 목록 조회")
    void getAlarms() throws Exception {
        // given
        final List<AlarmInfoResponse> alarmInfoResponses = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMe(AlarmInfoResponse.class, 5);
        final PageRequest pageRequest = PageRequest.of(0, 5);
        final Slice<AlarmInfoResponse> alarmInfoResponseSlice = new SliceImpl<>(alarmInfoResponses, pageRequest, false);
        final SliceResponse<AlarmInfoResponse> sliceResponse = SliceResponse.from(alarmInfoResponseSlice);
        given(alarmService.getAlarms(pageRequest)).willReturn(sliceResponse);
        MockHttpServletRequestBuilder request = RestDocumentationRequestBuilders.get(BASE_URL)
            .header("Authorization", "Bearer token")
            .param("page", "0")
            .param("size", "5");
        // when
        ResultActions result = mvc.perform(request);
        // then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                queryParameters(
                    parameterWithName("page").description("페이지 번호"),
                    parameterWithName("size").description("페이지 크기")
                ),
                responseFields(
                    fieldWithPath("content[].alarmId").description("알람 아이디"),
                    fieldWithPath("content[].title").description("알람 카테고리"),
                    fieldWithPath("content[].message").description("알람 내용"),
                    fieldWithPath("content[].isRead").description("읽음 여부"),
                    fieldWithPath("content[].createdAt").description("알람 생성 시간"),
                    fieldWithPath("page").description("페이지 정보"),
                    fieldWithPath("size").description("페이지 크기"),
                    fieldWithPath("hasNext").description("다음 페이지 존재 여부")
                )
            ));
    }

}