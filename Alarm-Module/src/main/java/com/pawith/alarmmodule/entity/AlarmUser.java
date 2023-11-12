package com.pawith.alarmmodule.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AlarmUser extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_user_id")
    private Long id;

    private String deviceToken;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    private Long userId;

    @Builder
    public AlarmUser(String deviceToken, DeviceType deviceType, Long userId) {
        this.deviceToken = deviceToken;
        this.deviceType = deviceType;
        this.userId = userId;
    }

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = Objects.requireNonNull(deviceToken, "deviceToken must be not null");
    }

    public void updateDeviceType(DeviceType deviceType) {
        this.deviceType = Objects.requireNonNull(deviceType, "deviceType must be not null");
    }
}
