package com.pawith.alarmmodule.entity.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmBody {
    private String message;
    private Long domainId;

    public AlarmBody(String message, Long domainId) {
        this.message = message;
        this.domainId = domainId;
    }
}
