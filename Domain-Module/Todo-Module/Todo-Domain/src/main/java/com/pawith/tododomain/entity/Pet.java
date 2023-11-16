package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import com.pawith.tododomain.entity.vo.PetSpecies;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
