package com.pawith.tododomain.entity.vo;

import com.pawith.commonmodule.util.DomainFieldUtils;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


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

    public void updatePetSpecies(String genus, String species) {
        this.genus = DomainFieldUtils.updateIfDifferent(genus, this.genus);
        this.species = DomainFieldUtils.updateIfDifferent(species, this.species);
    }
}
