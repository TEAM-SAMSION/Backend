package com.pawith.tododomain.entity.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
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
