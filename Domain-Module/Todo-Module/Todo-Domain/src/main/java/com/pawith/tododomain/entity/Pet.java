package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import com.pawith.commonmodule.util.DomainFieldUtils;
import com.pawith.tododomain.entity.vo.PetSpecies;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE pet SET is_deleted = true WHERE pet_id = ?")
@Where(clause = "is_deleted = false")
public class Pet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    private String name;
    private Integer age;
    private String description;
    private String imageUrl;

    @Embedded
    private PetSpecies petSpecies;

    private Boolean isDeleted=Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TodoTeam todoTeam;

    @Builder
    public Pet(String name, Integer age, String description, String imageUrl, PetSpecies petSpecies, TodoTeam todoTeam) {
        this.name = name;
        this.age = age;
        this.description = description;
        this.imageUrl = imageUrl;
        this.petSpecies = petSpecies;
        this.todoTeam = todoTeam;
    }


    public void updatePet(String imageUrl, String name, Integer age, String genus, String species, String description) {
        this.imageUrl = DomainFieldUtils.DomainValidateBuilder.builder(String.class)
                .newValue(imageUrl)
                .currentValue(this.imageUrl)
                .validate();
        this.name = DomainFieldUtils.DomainValidateBuilder.builder(String.class)
                .newValue(name)
                .currentValue(this.name)
                .validate();
        this.age = DomainFieldUtils.DomainValidateBuilder.builder(Integer.class)
                .newValue(age)
                .currentValue(this.age)
                .validate();
        this.description = DomainFieldUtils.DomainValidateBuilder.builder(String.class)
                .newValue(description)
                .currentValue(this.description)
                .validate();
        this.petSpecies.updatePetSpecies(genus, species);
    }
}
