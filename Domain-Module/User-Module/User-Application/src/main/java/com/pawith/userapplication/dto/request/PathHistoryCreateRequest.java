package com.pawith.userapplication.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PathHistoryCreateRequest {
    private String path;

    public PathHistoryCreateRequest(String path) {
        this.path = path;
    }

}
