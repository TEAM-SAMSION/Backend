package com.pawith.imagemodule.infrastructure.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.imagemodule.exception.FileError;
import com.pawith.imagemodule.exception.FileExtentionException;
import com.pawith.imagemodule.exception.FileUploadException;
import com.pawith.imagemodule.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApplicationService
@RequiredArgsConstructor
public class S3ImageUploadService implements ImageUploadService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> uploadImgList(List<MultipartFile> imgList) {
        if(CollectionUtils.isEmpty(imgList)) return List.of();
        List<String> uploadUrl = new ArrayList<>();
        for (MultipartFile img : imgList) {
            uploadUrl.add(uploadImg(img));
        }
        return uploadUrl;
    }

    public List<CompletableFuture<String>> uploadImgListAsync(List<MultipartFile> imgList){
        List<CompletableFuture<String>> imageCompletableFuture = new ArrayList<>();
        for(MultipartFile img : imgList){
            imageCompletableFuture.add(uploadImgAsync(img));
        }
        return imageCompletableFuture;
    }

    public CompletableFuture<String> uploadImgAsync(MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> uploadImg(file));
    }

    //단일 이미지 업로드
    public String uploadImg(MultipartFile file) {
        if(Objects.isNull(file)) return null;
        if(file.isEmpty()) return null;

        String originFileName = Normalizer.normalize(file.getOriginalFilename(), Normalizer.Form.NFC);
        String fileName = createFileName(originFileName);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadException(FileError.FILE_UPLOAD_ERROR);
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    //파일명 난수화
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    //파일 확장자 체크
    private String getFileExtension(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf('.'));
        if (!ext.equals(".jpg") && !ext.equals(".png") && !ext.equals(".jpeg") && !ext.equals(".svg+xml") && !ext.equals(".svg")) {
            throw new FileExtentionException(FileError.FILE_EXTENTION_ERROR);
        }
        return ext;
    }
}
