package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import com.pawith.commonmodule.util.DomainFieldUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "todo_team",
    uniqueConstraints = @UniqueConstraint(name = "team_code_unique", columnNames = "teamCode")
)

public class TodoTeam extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String teamCode;
    private String teamName;
    private String imageUrl;
    private String description;

    @Builder
    public TodoTeam(String teamCode, String teamName, String imageUrl, String description) {
        this.teamCode = teamCode;
        this.teamName = teamName;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public void updateTodoTeam(String teamName, String description, String imageUrl) {
        this.teamName = DomainFieldUtils.DomainValidateBuilder.builder(String.class)
            .newValue(teamName)
            .currentValue(this.teamName)
            .validate();
        this.description = DomainFieldUtils.DomainValidateBuilder.builder(String.class)
            .newValue(description)
            .currentValue(this.description)
            .validate();
        this.imageUrl = DomainFieldUtils.DomainValidateBuilder.builder(String.class)
            .newValue(imageUrl)
            .currentValue(this.imageUrl)
            .validate();
    }
}
