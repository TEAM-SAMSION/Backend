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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
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

    public Register findRegisterById(Long registerId){
        return findRegister(registerRepository::findById, registerId);
    }

    public List<Register> findAllRegistersByTodoTeamId(Long todoTeamId){
        return findRegisterList(registerRepository::findAllByTodoTeamId,todoTeamId);
    }

    public List<Register> findAllRegistersByIds(List<Long> registerIds){
        return findRegisterList(registerRepository::findAllByIds,registerIds);
    }

    public List<Register> findAllRegistersByTodoId(Long todoId) {
        return findRegisterList(registerRepository::findByTodoId,todoId);
    }

    public List<Long> findUserIdsByCategoryId(Long categoryId){
        return findRegisterList(registerRepository::findAllByCategoryId,categoryId).stream()
            .map(Register::getUserId)
            .collect(Collectors.toList());
    }

    public Integer countRegisterByTodoTeamId(Long todoTeamId){
        return registerRepository.countByTodoTeamId(todoTeamId);
    }

    public Integer findUserRegisterTerm (Long todoTeamId, Long userId){
        final Register register = findRegisterByTodoTeamIdAndUserId(todoTeamId, userId);
        return (int) ChronoUnit.DAYS.between(register.getCreatedAt().toLocalDate(), LocalDate.now());
    }

    private <T> Register findRegister(Function<T, Optional<Register>> method, T specificationData) {
        return registerOptionalHandle(() -> method.apply(specificationData));
    }

    private <T,U> Register findRegister(BiFunction<T,U,Optional<Register>> method,
                                        T specificationData1, U specificationData2) {
        return registerOptionalHandle(() -> method.apply(specificationData1, specificationData2));
    }

    private <T> List<Register> findRegisterList(Function<T, List<Register>> method, T specificationData) {
        return filterUnregister(method.apply(specificationData));
    }

    private Register registerOptionalHandle(Supplier<Optional<Register>> method){
        return filterUnregister(method.get())
            .orElseThrow(() -> new NotRegisterUserException(Error.NOT_REGISTER_USER));
    }

    private Optional<Register> filterUnregister(Optional<Register> registerOptional) {
        return registerOptional.filter(Register::getIsRegistered);
    }

    private List<Register> filterUnregister(List<Register> registers) {
        return registers.stream()
            .filter(Register::getIsRegistered)
            .collect(Collectors.toList());
    }
}
