package com.pawith.userapplication.mapper;

import com.pawith.userapplication.dto.request.WithdrawReasonCreateRequest;
import com.pawith.userdomain.entity.WithdrawReason;

public class WithdrawMapper {
        public static WithdrawReason toWithdrawReasonEntity(WithdrawReasonCreateRequest withDrawReasonCreateRequest) {
            return WithdrawReason.builder()
                    .reason(withDrawReasonCreateRequest.getReason())
                    .build();
        }
}
