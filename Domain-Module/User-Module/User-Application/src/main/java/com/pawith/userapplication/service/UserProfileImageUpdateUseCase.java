package com.pawith.userapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.imagedomain.service.ImageUploadService;
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
//        uploadImgAsync(request);
        final String imageUrl = imageUploadService.uploadImg(request);
        final User user = userUtils.getAccessUser();
        user.updateProfileImage(imageUrl);
    }

    /**
     * 비동기로 이미지 업로드, 테스트 좀 해보고 사용할지 생각해봐야할듯
     */
//    private CompletableFuture<Void> uploadImgAsync(MultipartFile request) {
//        return CompletableFuture.runAsync(() ->{
//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus status) {
//                    try {
//                        final CompletableFuture<String> imageUploadAsync = imageUploadUseCase.uploadImgAsync(request);
//                        final User user = UserUtils.getAccessUser();
//                        user.updateProfileImage(imageUploadAsync.join());
//                    }catch (Exception e) {
//                        status.setRollbackOnly();
//                    }
//                }
//            });
//        });
//    }
}
