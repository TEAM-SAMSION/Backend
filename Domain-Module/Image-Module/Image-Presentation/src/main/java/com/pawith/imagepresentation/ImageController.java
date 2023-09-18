package com.pawith.imagepresentation;

import com.pawith.imageapplication.dto.request.ImageUploadListRequest;
import com.pawith.imageapplication.dto.request.ImageUploadRequest;
import com.pawith.imageapplication.dto.response.ImageUploadListResponse;
import com.pawith.imageapplication.dto.response.ImageUploadResponse;
import com.pawith.imageapplication.service.ImageUploadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageUploadUseCase imageUploadUseCase;

    @PostMapping
    public ImageUploadResponse uploadImage(@ModelAttribute ImageUploadRequest request){
        return imageUploadUseCase.uploadImage(request);
    }

    @PostMapping("/list")
    public ImageUploadListResponse uploadImageList(@ModelAttribute ImageUploadListRequest request){
        return imageUploadUseCase.uploadImageList(request);
    }

}
