package com.pawith.alarmmodule.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AlarmUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_user_id")
    private Long id;

    private String deviceToken;

    private Long userId;

    @Builder
    public AlarmUser(String deviceToken, Long userId) {
        this.deviceToken = deviceToken;
        this.userId = userId;
    }

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = Objects.requireNonNull(deviceToken, "deviceToken must be not null");
    }
}
