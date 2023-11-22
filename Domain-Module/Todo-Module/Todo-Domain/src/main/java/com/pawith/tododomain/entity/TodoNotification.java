package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import com.pawith.commonmodule.util.DomainFieldUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoNotification extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_alarm_id")
    private Long id;

    private LocalTime notificationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Assign assign;

    @Builder
    public TodoNotification(LocalTime localTime, Assign assign) {
        this.notificationTime = localTime;
        this.assign = assign;
    }

    public void updateNotificationTime(LocalTime updateNotificationTime){
        this.notificationTime = DomainFieldUtils.DomainValidateBuilder.builder(LocalTime.class)
                .newValue(updateNotificationTime)
                .currentValue(this.notificationTime)
                .validate();
    }
}
