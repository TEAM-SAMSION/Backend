package com.pawith.userapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class UserProfileImageUpdateRequest {
    private MultipartFile profileImage;
}
