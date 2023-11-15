package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import java.util.Objects;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
        this.teamName = updateIfDifferent(teamName, this.teamName);
        this.description = updateIfDifferent(description, this.description);
        this.imageUrl = updateIfDifferent(imageUrl, this.imageUrl);
    }

    public <T> T updateIfDifferent(T newValue, T currentValue) {
        return Objects.equals(newValue, currentValue) ? currentValue : Objects.requireNonNullElse(newValue, currentValue);
    }

}
