package com.pawith.imagemodule.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ImageUploadService {
    List<String> uploadImgList(List<MultipartFile> imgList);

    List<CompletableFuture<String>> uploadImgListAsync(List<MultipartFile> imgList);

    CompletableFuture<String> uploadImgAsync(MultipartFile file);

    String uploadImg(MultipartFile file);
}
