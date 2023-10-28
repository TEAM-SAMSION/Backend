package com.pawith.userdomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PathHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "path_history_id")
    private Long id;

    private String path;

    @Builder
    public PathHistory(String path) {
        this.path = path;
    }
}
