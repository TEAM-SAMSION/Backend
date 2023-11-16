package com.pawith.todoapplication.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetInfoChangeRequest {
    private String name;
    private Integer age;
    private String genus;
    private String species;
    private String description;
}
