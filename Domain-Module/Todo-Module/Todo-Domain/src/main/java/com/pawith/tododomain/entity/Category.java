package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryStatus categoryStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TodoTeam todoTeam;

    @Builder
    public Category(String name, CategoryStatus categoryStatus, TodoTeam todoTeam) {
        this.name = name;
        this.categoryStatus = categoryStatus;
        this.todoTeam = todoTeam;
    }

    public void updateCategoryStatus(CategoryStatus categoryStatus) {
        if(this.categoryStatus.equals(categoryStatus))
            return;
        this.categoryStatus = Objects.requireNonNull(categoryStatus, "categoryStatus must be not null");
    }
}
