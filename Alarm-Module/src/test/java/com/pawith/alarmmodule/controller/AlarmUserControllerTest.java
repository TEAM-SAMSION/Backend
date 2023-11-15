package com.pawith.alarmmodule.controller;

import com.pawith.alarmmodule.service.AlarmUserService;
import com.pawith.alarmmodule.service.dto.request.DeviceTokenSaveRequest;
import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlarmUserController.class)
@DisplayName("AlarmUserController 테스트")
class AlarmUserControllerTest extends BaseRestDocsTest {

    private static final String BASE_URL = "/alarms";
    @MockBean
    private AlarmUserService alarmUserService;

    @Test
    @DisplayName("디바이스 토큰 저장")
    void postDeviceToken() throws Exception {
        //given
        DeviceTokenSaveRequest deviceTokenSaveRequest = FixtureMonkeyUtils.getConstructBasedFixtureMonkey().giveMeOne(DeviceTokenSaveRequest.class);
        MockHttpServletRequestBuilder request = RestDocumentationRequestBuilders.post(BASE_URL + "/token")
            .header("Authorization", "Bearer token")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(deviceTokenSaveRequest));
        //when
        ResultActions result = mvc.perform(request);
        //then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access 토큰")
                ),
                requestFields(
                    fieldWithPath("deviceToken").description("디바이스 토큰")
                )
            ));
    }
}