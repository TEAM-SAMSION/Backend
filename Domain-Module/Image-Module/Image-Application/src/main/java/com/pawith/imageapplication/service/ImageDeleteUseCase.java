package com.pawith.imageapplication.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.pawith.commonmodule.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.function.Function;

@ApplicationService
@RequiredArgsConstructor
@Slf4j
public class ImageDeleteUseCase<T> {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void deleteImgList(List<T> imgUrlList, Function<T, String> imageUrlExtractor) {
        if (!imgUrlList.isEmpty()) {
            for (T img : imgUrlList) {
                String imgUrl = imageUrlExtractor.apply(img);
                deleteImg(imgUrl);
            }
        }
    }

    /*
    개별 이미지 삭제
    */
    public void deleteImg(String originImgUrl) {
        if (originImgUrl == null) return;
        try {
            amazonS3.deleteObject(bucket, originImgUrl.split("/")[3]);
        } catch (AmazonServiceException e) {
            throw new FileDeleteException(Error.FILE_DELETE_ERROR);
        }
    }
}
