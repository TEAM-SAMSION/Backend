package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE todo SET is_deleted = true WHERE todo_id=?")
@Where(clause = "is_deleted=false")
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    private String description;
    private LocalDate scheduledDate;

    @Enumerated(EnumType.STRING)
    private CompletionStatus completionStatus;

    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Todo(String description, LocalDate scheduledDate, Category category) {
        this.description = description;
        this.scheduledDate = scheduledDate;
        this.completionStatus = CompletionStatus.INCOMPLETE;
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

    public void updateCompletionStatus(CompletionStatus completionStatus){
        if(this.completionStatus.equals(completionStatus))
            return;
        this.completionStatus = Objects.requireNonNull(completionStatus, "nickname must be not null");
    }
}
