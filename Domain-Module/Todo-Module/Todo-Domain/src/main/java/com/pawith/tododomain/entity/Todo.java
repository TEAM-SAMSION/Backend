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

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE todo SET is_deleted = true WHERE todo_id=? and version=?")
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

    private Long creatorId;

    @Version
    private Long version;

    @Builder
    public Todo(String description, LocalDate scheduledDate, Category category, Long creatorId) {
        this.description = description;
        this.scheduledDate = scheduledDate;
        this.completionStatus = CompletionStatus.INCOMPLETE;
        this.category = category;
        this.creatorId = creatorId;
    }

    public void updateScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = DomainFieldUtils.DomainValidateBuilder.builder(LocalDate.class)
                .newValue(scheduledDate)
                .currentValue(this.scheduledDate)
                .validate();
    }

    public void updateDescription(String description) {
        this.description = DomainFieldUtils.DomainValidateBuilder.builder(String.class)
                .newValue(description)
                .currentValue(this.description)
                .validate();
    }

    public void updateCompletionStatus(boolean isAllCompleteTodo) {
        this.completionStatus = isAllCompleteTodo ? CompletionStatus.COMPLETE : CompletionStatus.INCOMPLETE;
    }

    public boolean isTodoCreator(Long creatorId) {
        return Objects.equals(this.creatorId, creatorId);
    }
}
