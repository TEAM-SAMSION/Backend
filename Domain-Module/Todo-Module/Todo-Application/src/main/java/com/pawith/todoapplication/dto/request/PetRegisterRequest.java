package com.pawith.todoapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetRegisterRequest {
    private String name;
    private Integer age;
    private String petGenus; // 과
    private String petSpecies; // 종
    private String description;
}
