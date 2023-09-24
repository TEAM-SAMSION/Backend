package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.exception.NotRegisterUserException;
import com.pawith.tododomain.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterQueryService {
    private final RegisterRepository registerRepository;

    public Slice<Register> findRegisterSliceByUserId(Long userId, Pageable pageable) {
        return registerRepository.findAllByUserId(userId, pageable);
    }

    public Register findRegisterByTodoTeamIdAndUserId(Long todoTeamId, Long userId) {
        return registerRepository.findByTodoTeamIdAndUserId(todoTeamId, userId)
            .orElseThrow(() -> new NotRegisterUserException(Error.NOT_REGISTER_USER));
    }

    public CompletableFuture<Register> findRegisterByIdAsync(Long registerId){
        return CompletableFuture.supplyAsync(() -> findRegisterById(registerId));
    }

    public Register findRegisterById(Long registerId){
        return findRegister(registerRepository::findById, registerId);
    }

    private <T> Register findRegister(Function<T, Optional<Register>> method, T specificationData) {
        return method.apply(specificationData)
            .orElseThrow(() -> new NotRegisterUserException(Error.NOT_REGISTER_USER));
    }

    public List<Register> findAllRegisters(Long userId, Long todoTeamId){
        if(!registerRepository.existsByTodoTeamIdAndUserId(todoTeamId, userId)){
            return List.of();
        }
        return registerRepository.findAllByTodoTeamId(todoTeamId);
    }
}
