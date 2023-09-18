package com.pawith.imageapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Getter
public class ImageUploadListRequest {
    private List<MultipartFile> imageFileList;
}
