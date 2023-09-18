package com.pawith.imageapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.imageapplication.dto.request.ImageUploadListRequest;
import com.pawith.imageapplication.dto.request.ImageUploadRequest;
import com.pawith.imageapplication.dto.response.ImageUploadListResponse;
import com.pawith.imageapplication.dto.response.ImageUploadResponse;
import com.pawith.imagedomain.service.ImageUploadService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class ImageUploadUseCase {
    private final ImageUploadService imageUploadService;

    public ImageUploadResponse uploadImage(ImageUploadRequest request) {
        return new ImageUploadResponse(imageUploadService.uploadImg(request.getImageFile()));
    }

    public ImageUploadListResponse uploadImageList(ImageUploadListRequest request) {
        return new ImageUploadListResponse(imageUploadService.uploadImgList(request.getImageFileList()));
    }
}
