package com.pawith.usermodule.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.imageapplication.service.ImageUploadUseCase;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@ApplicationService
@RequiredArgsConstructor
public class UserProfileImageUpdateUseCase {

    private final ImageUploadUseCase imageUploadUseCase;

    @Transactional
    public void updateUserProfileImage(MultipartFile request) {
//        uploadImgAsync(request);
        final String imageUrl = imageUploadUseCase.uploadImg(request);
        final User user = UserUtils.getAccessUser();
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
