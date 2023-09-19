package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TodoTeam todoTeam;

    @Builder
    public Pet(String name, Integer age, String description, String imageUrl, TodoTeam todoTeam) {
        this.name = name;
        this.age = age;
        this.description = description;
        this.imageUrl = imageUrl;
        this.todoTeam = todoTeam;
    }
}
