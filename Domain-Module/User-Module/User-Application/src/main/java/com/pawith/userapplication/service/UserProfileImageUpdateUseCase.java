package com.pawith.userapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.imagemodule.service.ImageUploadService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@ApplicationService
@RequiredArgsConstructor
public class UserProfileImageUpdateUseCase {

    private final UserUtils userUtils;
    private final ImageUploadService imageUploadService;

    @Transactional
    public void updateUserProfileImage(MultipartFile request) {
        final String imageUrl = imageUploadService.uploadImg(request);
        final User user = userUtils.getAccessUser();
        user.updateProfileImage(imageUrl);
    }

}
