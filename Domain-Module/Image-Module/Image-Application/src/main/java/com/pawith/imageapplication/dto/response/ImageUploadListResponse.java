package com.pawith.imageapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ImageUploadListResponse {
    private final List<String> imageUrls;
}
