package com.pawith.userapplication.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.imagedomain.service.ImageUploadService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.BDDMockito.given;

@Slf4j
@UnitTestConfig
@DisplayName("UserProfileImageUpdateUseCase 테스트")
class UserProfileImageUpdateUseCaseTest {

    @Mock
    private UserUtils userUtils;
    @Mock
    private ImageUploadService imageUploadService;

    private UserProfileImageUpdateUseCase userProfileImageUpdateUseCase;

    @BeforeEach
    void init() {
        userProfileImageUpdateUseCase = new UserProfileImageUpdateUseCase(userUtils, imageUploadService);
    }

    @Test
    @DisplayName("유저 프로필 이미지를 변경한다.")
    void updateUserProfileImage() {
        //given
        final MultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpg", "test image".getBytes());
        final String imageUrl = FixtureMonkey.create().giveMeOne(String.class);
        final User mockUser= FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(User.class);
        given(imageUploadService.uploadImg(mockFile)).willReturn(imageUrl);
        given(userUtils.getAccessUser()).willReturn(mockUser);
        //when
        userProfileImageUpdateUseCase.updateUserProfileImage(mockFile);
        //then
        Assertions.assertThat(mockUser.getImageUrl()).isEqualTo(imageUrl);
    }

}