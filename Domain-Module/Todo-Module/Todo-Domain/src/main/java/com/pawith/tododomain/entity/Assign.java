package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE assign SET is_deleted = true WHERE assign_id=?")
@Where(clause = "is_deleted=false")
public class Assign extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assign_id")
    private Long id;

    private Boolean isDeleted = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    private CompletionStatus completionStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_id")
    private Register register;

    @Builder
    public Assign(Todo todo, Register register) {
        this.todo = todo;
        this.register = register;
        this.completionStatus = CompletionStatus.INCOMPLETE;
    }

    public void updateCompletionStatus() {
        if(this.completionStatus.equals(CompletionStatus.COMPLETE))
            this.completionStatus = CompletionStatus.INCOMPLETE;
        else
            this.completionStatus = CompletionStatus.COMPLETE;
    }
}
