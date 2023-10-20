package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.service.RegisterQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class ChangeRegisterUseCase {

    private final RegisterQueryService registerQueryService;

    public void changeAuthority(Long registerId, String authority) {
        Register register = registerQueryService.findRegisterById(registerId);
        register.updateAuthority(authority);
    }

}
