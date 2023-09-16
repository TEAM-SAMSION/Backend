package com.pawith.imageapplication.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ImageUploadUseCase {
    List<String> uploadImgList(List<MultipartFile> imgList);

    CompletableFuture<String> uploadImgAsync(MultipartFile file);

    String uploadImg(MultipartFile file);
}
