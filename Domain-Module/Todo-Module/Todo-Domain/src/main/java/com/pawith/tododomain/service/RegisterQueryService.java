package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.exception.NotRegisterUserException;
import com.pawith.tododomain.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterQueryService {
    private final RegisterRepository registerRepository;

    public Slice<Register> findRegisterSliceByUserId(Long userId, Pageable pageable) {
        return registerRepository.findAllByUserId(userId, pageable);
    }

    public Register findRegisterByTodoTeamIdAndUserId(Long todoTeamId, Long userId) {
        return findRegister(registerRepository::findByTodoTeamIdAndUserId, todoTeamId, userId);
    }

    public Register findPresidentRegisterByTodoTeamId(Long todoTeamId){
        return findRegister(registerRepository::findByTodoTeamIdAndAuthority, todoTeamId, Authority.PRESIDENT);
    }

    public List<Register> findAllRegisterByIds(List<Long> registerIds){
        return registerRepository.findAllByIds(registerIds);
    }

    public Register findRegisterById(Long registerId){
        return findRegister(registerRepository::findById, registerId);
    }

    public List<Long> findUserIdsByCategoryId(Long categoryId){
        return registerRepository.findAllByCategoryId(categoryId).stream()
            .map(Register::getUserId)
            .collect(Collectors.toList());
    }

    public List<Register> findAllRegisters(Long userId, Long todoTeamId){
        if(!registerRepository.existsByTodoTeamIdAndUserId(todoTeamId, userId)){
            return List.of();
        }
        return registerRepository.findAllByTodoTeamId(todoTeamId);
    }

    public List<Register> findAllRegisterByTodoId(Long todoId) {
        return registerRepository.findByTodoId(todoId);
    }

    public Integer countRegisterByTodoTeamId(Long todoTeamId){
        return registerRepository.countByTodoTeamId(todoTeamId);
    }

    private <T> Register findRegister(Function<T, Optional<Register>> method, T specificationData) {
        return method.apply(specificationData)
            .orElseThrow(() -> new NotRegisterUserException(Error.NOT_REGISTER_USER));
    }

    private <T,U> Register findRegister(BiFunction<T,U,Optional<Register>> method,
                                        T specificationData1, U specificationData2) {
        return method.apply(specificationData1, specificationData2)
            .orElseThrow(() -> new NotRegisterUserException(Error.NOT_REGISTER_USER));
    }
}
