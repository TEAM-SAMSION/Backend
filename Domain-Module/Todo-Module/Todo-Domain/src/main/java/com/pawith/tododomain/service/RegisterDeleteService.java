package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@RequiredArgsConstructor
@Transactional
public class RegisterDeleteService {

    private final RegisterRepository registerRepository;

    public void deleteRegister(Register requestRegister){
        registerRepository.delete(requestRegister);
    }

    public void deleteRegisterByRegisterIds(List<Long> registerIds){
        registerRepository.deleteByRegisterIds(registerIds);
    }
}
