package com.pawith.tododomain.entity.vo;

import java.util.Objects;
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

    public void updatePetSpecies(String genus, String species) {
        this.genus = updateIfDifferent(genus, this.genus);
        this.species = updateIfDifferent(species, this.species);
    }

    public <T> T updateIfDifferent(T newValue, T currentValue) {
        return Objects.equals(newValue, currentValue) ? currentValue : Objects.requireNonNullElse(newValue, currentValue);
    }
}
