package com.pawith.alarmmodule.entity;

import com.pawith.alarmmodule.entity.vo.AlarmBody;
import com.pawith.commonmodule.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @Embedded
    private AlarmBody alarmBody;
    private Boolean isRead = Boolean.FALSE;

    private String alarmCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_user_id")
    private AlarmUser alarmUser;

    @Builder
    public Alarm(AlarmBody alarmBody, String alarmCategory, AlarmUser alarmUser) {
        this.alarmBody = alarmBody;
        this.alarmCategory = alarmCategory;
        this.alarmUser = alarmUser;
    }
}
