package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoTeam extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String teamCode;
    private String teamName;
    private String imageUrl;

    @Builder
    public TodoTeam(String teamCode, String teamName, String imageUrl) {
        this.teamCode = teamCode;
        this.teamName = teamName;
        this.imageUrl = imageUrl;
    }
}
