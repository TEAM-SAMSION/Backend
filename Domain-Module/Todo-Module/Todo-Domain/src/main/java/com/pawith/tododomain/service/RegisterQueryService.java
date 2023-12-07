package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.exception.NotRegisterUserException;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterQueryService {
    private final RegisterRepository registerRepository;

    public Slice<Register> findRegisterSliceByUserId(Long userId, Pageable pageable) {
        return registerRepository.findAllByUserIdQuery(userId, pageable);
    }

    public List<Register> findRegisterListByUserIdWithTodoTeam(Long userId) {
        return registerRepository.findAllByUserIdWithTodoTeamFetchQuery(userId);
    }

    public Register findRegisterByTodoTeamIdAndUserId(Long todoTeamId, Long userId) {
        return findRegister(() -> registerRepository.findByTodoTeamIdAndUserId(todoTeamId, userId));
    }

    public Register findPresidentRegisterByTodoTeamId(Long todoTeamId){
        return findRegister(() -> registerRepository.findByTodoTeamIdAndAuthority(todoTeamId, Authority.PRESIDENT));
    }

    public Register findRegisterById(Long registerId){
        return findRegister(() -> registerRepository.findById(registerId));
    }

    public List<Register> findAllRegistersByTodoTeamId(Long todoTeamId){
        return findRegisterList(() -> registerRepository.findAllByTodoTeamId(todoTeamId));
    }

    public List<Register> findAllRegistersByIds(List<Long> registerIds){
        return findRegisterList(() -> registerRepository.findAllByIdsQuery(registerIds));
    }

    public List<Register> findAllRegistersByTodoId(Long todoId) {
        return findRegisterList(() -> registerRepository.findByTodoIdQuery(todoId));
    }

    public List<Register> findAllRegistersByCategoryId(Long categoryId) {
        return registerRepository.findAllByCategoryIdQuery(categoryId);
    }

    public List<Register> findAllRegistersByUserId(Long userId){
        return findRegisterList(() -> registerRepository.findAllByUserId(userId));
    }

    public List<Long> findUserIdsByCategoryId(Long categoryId){
        return findRegisterList(() -> registerRepository.findAllByCategoryIdQuery(categoryId)).stream()
            .map(Register::getUserId)
            .collect(Collectors.toList());
    }

    public Integer countRegisterByTodoTeamId(Long todoTeamId){
        return registerRepository.countByTodoTeamIdQuery(todoTeamId);
    }

    public Integer findUserRegisterTerm (Long todoTeamId, Long userId){
        final Register register = findRegisterByTodoTeamIdAndUserId(todoTeamId, userId);
        return (int) ChronoUnit.DAYS.between(register.getCreatedAt().toLocalDate(), LocalDate.now());
    }

    public Register findLatestTodoTeam(Long userId){
        return findRegister(() -> registerRepository.findLatestRegisterByUserIdQuery(userId));
    }
    private Register findRegister(Supplier<Optional<Register>> optionalSupplier){
        return registerOptionalHandle(optionalSupplier);
    }

    private <T> List<Register> findRegisterList(Supplier<List<Register>> listSupplier) {
        return filterUnregister(listSupplier.get());
    }

    private Register registerOptionalHandle(Supplier<Optional<Register>> method){
        return filterUnregister(method)
            .orElseThrow(() -> new NotRegisterUserException(TodoError.NOT_REGISTER_USER));
    }

    private Optional<Register> filterUnregister(Supplier<Optional<Register>> registerOptional) {
        return registerOptional.get()
            .filter(Register::isRegistered);
    }

    private List<Register> filterUnregister(List<Register> registers) {
        return registers.stream()
            .filter(Register::isRegistered)
            .collect(Collectors.toList());
    }
}
