package com.pawith.alarmmodule.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import com.pawith.commonmodule.util.DomainFieldUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        this.deviceToken = DomainFieldUtils.DomainValidateBuilder.builder(String.class)
            .currentValue(this.deviceToken)
            .newValue(deviceToken)
            .validate();
    }
}
