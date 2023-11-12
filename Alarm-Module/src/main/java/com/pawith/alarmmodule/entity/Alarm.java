package com.pawith.alarmmodule.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    private String body;
    private Boolean isRead = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    private AlarmCategory alarmCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_user_id")
    private AlarmUser alarmUser;

    @Builder
    public Alarm(String body, Boolean isRead, AlarmCategory alarmCategory, AlarmUser alarmUser) {
        this.body = body;
        this.isRead = isRead;
        this.alarmCategory = alarmCategory;
        this.alarmUser = alarmUser;
    }
}
