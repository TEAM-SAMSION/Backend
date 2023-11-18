package com.pawith.userapplication.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WithdrawReasonCreateRequest {
    private String reason;

    public WithdrawReasonCreateRequest(String reason) {
        this.reason = reason;
    }
}
