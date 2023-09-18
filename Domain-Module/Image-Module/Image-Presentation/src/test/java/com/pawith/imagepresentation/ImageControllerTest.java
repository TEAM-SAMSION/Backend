package com.pawith.imagepresentation;

import com.pawith.commonmodule.BaseRestDocsTest;
import com.pawith.imageapplication.dto.response.ImageUploadListResponse;
import com.pawith.imageapplication.dto.response.ImageUploadResponse;
import com.pawith.imageapplication.service.ImageUploadUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ImageController.class)
@DisplayName("ImageController 테스트")
class ImageControllerTest extends BaseRestDocsTest {

    @MockBean
    private ImageUploadUseCase imageUploadUseCase;

    private static final String IMAGE_UPLOAD_URL = "/images";

    @Test
    @DisplayName("이미지 하나 업로드 테스트")
    void uploadImage() throws Exception {
        // given
        final MockMultipartFile image = new MockMultipartFile("imageFile", "imageFile.png", "image/png", "imageFile".getBytes());
        final String imageUrl = "https://image.com";
        given(imageUploadUseCase.uploadImage(any())).willReturn(new ImageUploadResponse(imageUrl));
        MockHttpServletRequestBuilder request = multipart(IMAGE_UPLOAD_URL)
            .file(image)
            .header("Authorization", "Bearer accessToken")
            .contentType(MediaType.MULTIPART_FORM_DATA);
        // when
        ResultActions result = mvc.perform(request);
        // then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access token")
                ),
                requestParts(
                    partWithName("imageFile").description("이미지 파일")
                ),
                responseFields(
                    fieldWithPath("imageUrl").description("이미지 url")
                )
            ));
    }

    @Test
    @DisplayName("이미지 여러개 업로드 테스트")
    void uploadImageList() throws Exception {
        // given
        final MockMultipartFile image1 = new MockMultipartFile("imageFileList", "imageFile1.png", "image/png", "imageFile1".getBytes());
        final MockMultipartFile image2 = new MockMultipartFile("imageFileList", "imageFile2.png", "image/png", "imageFile2".getBytes());
        final String imageUrl1 = "https://image1.com";
        final String imageUrl2 = "https://image2.com";
        given(imageUploadUseCase.uploadImageList(any())).willReturn(new ImageUploadListResponse(List.of(imageUrl1, imageUrl2)));
        MockHttpServletRequestBuilder request = multipart(IMAGE_UPLOAD_URL + "/list")
            .file(image1)
            .file(image2)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .header("Authorization", "Bearer accessToken");
        // when
        ResultActions result = mvc.perform(request);
        // then
        result.andExpect(status().isOk())
            .andDo(resultHandler.document(
                requestHeaders(
                    headerWithName("Authorization").description("access token")
                ),
                requestParts(
                    partWithName("imageFileList").description("이미지 파일")
                ),
                responseFields(
                    fieldWithPath("imageUrls[]").description("이미지 url")
                )
            ));
    }

}