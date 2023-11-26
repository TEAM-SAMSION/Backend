package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import com.pawith.commonmodule.util.DomainFieldUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE category SET is_deleted = true WHERE category_id = ?")
@Where(clause = "is_deleted = false")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryStatus categoryStatus;

    private Boolean isDeleted=Boolean.FALSE;

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
        this.categoryStatus = DomainFieldUtils.DomainValidateBuilder.builder(CategoryStatus.class)
                .newValue(categoryStatus)
                .currentValue(this.categoryStatus)
                .validate();
    }

    public void updateCategoryName(String categoryName) {
        this.name = DomainFieldUtils.DomainValidateBuilder.builder(String.class)
                .newValue(categoryName)
                .currentValue(this.name)
                .validate();
    }
}
