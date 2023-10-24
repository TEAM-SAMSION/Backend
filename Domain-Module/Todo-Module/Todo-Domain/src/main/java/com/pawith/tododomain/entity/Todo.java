package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    private String description;
    private LocalDate scheduledDate;

    @Enumerated(EnumType.STRING)
    private TodoStatus todoStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Todo(String description, LocalDate scheduledDate, Category category) {
        this.description = description;
        this.scheduledDate = scheduledDate;
        this.todoStatus = TodoStatus.INCOMPLETE;
        this.category = category;
    }

    public void updateScheduledDate(LocalDate scheduledDate) {
        if(this.scheduledDate.equals(scheduledDate))
            return;
        this.scheduledDate = Objects.requireNonNull(scheduledDate, "nickname must be not null");
    }

    public void updateDescription(String description) {
        if(this.description.equals(description))
            return;
        this.description = Objects.requireNonNull(description, "nickname must be not null");
    }
}
