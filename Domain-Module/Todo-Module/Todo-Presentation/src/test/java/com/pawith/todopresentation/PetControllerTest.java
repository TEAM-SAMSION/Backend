package com.pawith.todopresentation;

import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.response.PetInfoResponse;
import com.pawith.todoapplication.service.PetChangeUseCase;
import com.pawith.todoapplication.service.PetCreateUseCase;
import com.pawith.todoapplication.service.PetDeleteUseCase;
import com.pawith.todoapplication.service.PetGetUseCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(PetController.class)
@DisplayName("PetController 테스트")
public class PetControllerTest extends BaseRestDocsTest {
    @MockBean
    private PetGetUseCase petGetUseCase;
    @MockBean
    private PetCreateUseCase petCreateUseCase;
    @MockBean
    private PetDeleteUseCase petDeleteUseCase;
    @MockBean
    private PetChangeUseCase petChangeUseCase;

    private static final String PET_REQUEST_URL = "/teams";
    private static final String PET_TEAM_CREATE_INFO = "{\n" +
            "        \"name\" : \"이름이 뭐에요~\",\n" +
            "        \"age\" : 2,\n" +
            "        \"description\" : \"귀엽습니다\",\n" +
            "        \"genus\" : \"과\",\n" +
            "        \"species\" : \"종\"\n" +
            "    }";

    @Test
    @DisplayName("팀의 펫 정보 조회 API 테스트")
    void getTodoTeamPets() throws Exception {
        // given
        final Long testTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final List<PetInfoResponse> petInfoResponses = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(PetInfoResponse.class, 2);
        given(petGetUseCase.getTodoTeamPets(testTeamId)).willReturn(ListResponse.from(petInfoResponses));
        MockHttpServletRequestBuilder request = get(PET_REQUEST_URL + "/{todoTeamId}/pets", testTeamId)
                .header("Authorization", "Bearer accessToken");
        // when
        ResultActions result = mvc.perform(request);
        // then
        // then
        result.andExpect(status().isOk())
                .andDo(resultHandler.document(
                        requestHeaders(
                                headerWithName("Authorization").description("access 토큰")
                        ),
                        pathParameters(
                                parameterWithName("todoTeamId").description("TodoTeam의 Id")
                        ),
                        responseFields(
                                fieldWithPath("content[].petId").description("Pet의 Id"),
                                fieldWithPath("content[].imageUrl").description("Pet의 이미지 URL"),
                                fieldWithPath("content[].name").description("Pet의 이름"),
                                fieldWithPath("content[].age").description("Pet의 나이"),
                                fieldWithPath("content[].genus").description("Pet의 종"),
                                fieldWithPath("content[].species").description("Pet의 품종"),
                                fieldWithPath("content[].description").description("Pet의 설명")
                        )
                ));
    }

    @Test
    @DisplayName("펫 정보 저장 API 테스트")
    void postTodoTeamPet() throws Exception {
        // given
        final Long testTeamId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final MockMultipartFile mockMultipartFilePet = new MockMultipartFile("petImageFile", "petImageFile", "image/jpeg", "image".getBytes());
        final MockMultipartFile petCreateInfo = new MockMultipartFile("petCreateInfo", "", MediaType.APPLICATION_JSON_VALUE, PET_TEAM_CREATE_INFO.getBytes());
        MockHttpServletRequestBuilder request = multipart(PET_REQUEST_URL + "/{todoTeamId}/pets", testTeamId)
                .file(mockMultipartFilePet)
                .file(petCreateInfo)
                .accept(MediaType.APPLICATION_JSON)
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
                                parameterWithName("todoTeamId").description("TodoTeam의 Id")
                        ),
                        requestPartFields("petCreateInfo",
                                fieldWithPath("name").description("Pet의 이름"),
                                fieldWithPath("age").description("Pet의 나이"),
                                fieldWithPath("genus").description("Pet의 종"),
                                fieldWithPath("species").description("Pet의 품종"),
                                fieldWithPath("description").description("Pet의 설명")
                        )
                ));
    }

    @Test
    @DisplayName("펫 정보 삭제 API 테스트")
    void deleteTodoTeamPet() throws Exception {
        // given
        final Long testPetId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        MockHttpServletRequestBuilder request = delete(PET_REQUEST_URL + "/pets/{petId}", testPetId)
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
                                parameterWithName("petId").description("Pet의 Id")
                        )
                ));
    }

    @Test
    @DisplayName("펫 정보 수정 API 테스트")
    void putTodoTeamPet() throws Exception {
        // given
        final Long testPetId = FixtureMonkeyUtils.getJavaTypeBasedFixtureMonkey().giveMeOne(Long.class);
        final MockMultipartFile mockMultipartFilePet = new MockMultipartFile("petImageFile", "petImageFile", "image/jpeg", "image".getBytes());
        final MockMultipartFile petCreateInfo = new MockMultipartFile("petUpdateInfo", "", MediaType.APPLICATION_JSON_VALUE, PET_TEAM_CREATE_INFO.getBytes());
        MockHttpServletRequestBuilder request = multipart(PET_REQUEST_URL + "/pets/{petId}", testPetId)
                .file(mockMultipartFilePet)
                .file(petCreateInfo)
                .accept(MediaType.APPLICATION_JSON)
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
                                parameterWithName("petId").description("Pet의 Id")
                        ),
                        requestPartFields("petUpdateInfo",
                                fieldWithPath("name").description("Pet의 이름"),
                                fieldWithPath("age").description("Pet의 나이"),
                                fieldWithPath("genus").description("Pet의 종"),
                                fieldWithPath("species").description("Pet의 품종"),
                                fieldWithPath("description").description("Pet의 설명")
                        )
                ));
    }

}
