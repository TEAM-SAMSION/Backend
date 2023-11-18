package com.pawith.userapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.userapplication.dto.request.WithdrawReasonCreateRequest;
import com.pawith.userapplication.mapper.WithdrawMapper;
import com.pawith.userdomain.entity.WithdrawReason;
import com.pawith.userdomain.service.WithDrawSaveService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class WithdrawReasonCreateUseCase {
    private final WithDrawSaveService withDrawSaveService;

    public void createWithdrawReason(WithdrawReasonCreateRequest request) {
        WithdrawReason withdrawReason = WithdrawMapper.toWithdrawReasonEntity(request);
        withDrawSaveService.save(withdrawReason);
    }
}
