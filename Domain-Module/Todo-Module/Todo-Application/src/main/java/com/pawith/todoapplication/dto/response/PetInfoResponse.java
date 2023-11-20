package com.pawith.todoapplication.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetInfoResponse {
    private Long petId;
    private String imageUrl;
    private String name;
    private Integer age;
    private String genus;
    private String species;
    private String description;
}
