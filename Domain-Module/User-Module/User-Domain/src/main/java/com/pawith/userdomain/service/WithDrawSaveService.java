package com.pawith.userdomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.userdomain.entity.WithdrawReason;
import com.pawith.userdomain.repository.WithdrawReasonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class WithDrawSaveService {
    private final WithdrawReasonRepository withdrawReasonRepository;
    @Transactional
    public void save(WithdrawReason reason) {
        withdrawReasonRepository.save(reason);
    }
}
