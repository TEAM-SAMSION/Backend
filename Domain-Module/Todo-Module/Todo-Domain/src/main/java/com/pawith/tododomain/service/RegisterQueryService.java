package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.exception.NotRegisterUserException;
import com.pawith.tododomain.exception.RegisterNotFoundException;
import com.pawith.tododomain.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegisterQueryService {
    private final RegisterRepository registerRepository;

    public Slice<Register> findRegisterSliceByUserId(Long userId, Pageable pageable) {
        return registerRepository.findAllByUserId(userId, pageable);
    }

    public Register findRegisterByTodoTeamIdAndUserId(Long todoTeamId, Long userId) {
        return registerRepository.findByTodoTeamIdAndUserId(todoTeamId, userId)
            .orElseThrow(() -> new NotRegisterUserException(Error.NOT_REGISTER_USER));
    }

    public Register findRecentRegisterByUserId(Long userId) {
        return registerRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new RegisterNotFoundException(Error.REGISTER_NOT_FOUND));
    }
}
