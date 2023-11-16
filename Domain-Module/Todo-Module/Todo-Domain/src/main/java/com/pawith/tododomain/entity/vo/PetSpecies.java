package com.pawith.tododomain.entity.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetSpecies {
    private String genus; // 과
    private String species; // 종

    @Builder
    public PetSpecies(String genus, String species) {
        this.genus = genus;
        this.species = species;
    }
}
