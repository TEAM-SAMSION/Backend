package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import com.pawith.tododomain.entity.vo.PetSpecies;
import java.util.Objects;
import lombok.*;

import javax.persistence.*;
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


    public void updatePet(String imageUrl, String name, Integer age, String description, String species, String genus) {
        this.imageUrl = updateIfDifferent(imageUrl, this.imageUrl);
        this.name = updateIfDifferent(name, this.name);
        this.age = updateIfDifferent(age, this.age);
        this.description = updateIfDifferent(description, this.description);
        this.petSpecies.updatePetSpecies(genus, species);
    }

    public <T> T updateIfDifferent(T newValue, T currentValue) {
        return Objects.equals(newValue, currentValue) ? currentValue : Objects.requireNonNullElse(newValue, currentValue);
    }
}
