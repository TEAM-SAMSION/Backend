package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.request.AuthorityChangeRequest;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.service.RegisterQueryService;
import com.pawith.tododomain.service.RegisterValidateService;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class ChangeRegisterUseCase {

    private final RegisterQueryService registerQueryService;
    private final RegisterValidateService registerValidateService;
    private final UserUtils userUtils;

    public void changeAuthority(Long todoTeamId, Long registerId, AuthorityChangeRequest request) {
        final User user = userUtils.getAccessUser();
        Register userRegister = registerQueryService.findRegisterByTodoTeamIdAndUserId(todoTeamId, user.getId());
        Register register = registerQueryService.findRegisterById(registerId);
        Authority authority = request.getAuthority();
        if (userRegister.isPresident() && authority.equals(Authority.PRESIDENT)) {
            userRegister.updateAuthority(Authority.MEMBER);
        } else {
            registerValidateService.validateAuthorityChangeable(userRegister, authority);
        }

        register.updateAuthority(authority);
    }

}
