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
    private String petName;
    private Integer petAge;
    private String petGenus;
    private String petSpecies;
    private String petDescription;
}
