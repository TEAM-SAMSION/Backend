package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.response.RegisterListResponse;
import com.pawith.todoapplication.dto.response.RegisterSimpleInfoResponse;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegistersGetUseCase {

    private final UserUtils userUtils;
    private final RegisterQueryService registerQueryService;
    private final UserQueryService userQueryService;

    /**
     * TODO : 성능 최적화 방안으로 캐시 고민
     */
    public RegisterListResponse getRegisters(final Long teamId) {
        final User user = userUtils.getAccessUser();
        final List<Register> allRegisters = registerQueryService.findAllRegisters(user.getId(), teamId);
        final List<RegisterSimpleInfoResponse> registerSimpleInfoResponses = allRegisters.stream()
            .map(register -> {
                final User findUser = userQueryService.findById(register.getUserId());
                return new RegisterSimpleInfoResponse(register.getId(), findUser.getNickname());
            })
            .collect(Collectors.toList());
        return new RegisterListResponse(registerSimpleInfoResponses);
    }
}