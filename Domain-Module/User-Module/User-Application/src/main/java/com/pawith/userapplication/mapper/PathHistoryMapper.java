package com.pawith.userapplication.mapper;

import com.pawith.userapplication.dto.request.PathHistoryCreateRequest;
import com.pawith.userdomain.entity.PathHistory;

public class PathHistoryMapper {

    public static PathHistory toPathHistoryEntity(PathHistoryCreateRequest pathHistoryCreateRequest) {
        return PathHistory.builder()
                .path(pathHistoryCreateRequest.getPath())
                .build();
    }
}
