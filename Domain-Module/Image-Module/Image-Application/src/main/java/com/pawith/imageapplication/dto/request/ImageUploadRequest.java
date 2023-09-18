package com.pawith.imageapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class ImageUploadRequest {
    private MultipartFile imageFile;
}
