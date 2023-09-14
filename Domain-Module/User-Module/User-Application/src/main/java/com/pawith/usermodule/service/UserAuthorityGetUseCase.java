package com.pawith.usermodule.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.usermodule.service.dto.UserAuthorityInfoResponse;
import com.pawith.usermodule.entity.UserAuthority;
import com.pawith.usermodule.utils.UserUtils;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class UserAuthorityGetUseCase {

    private final UserAuthorityQueryService userAuthorityQueryService;

    public UserAuthorityInfoResponse getUserAuthority() {
        final String email = UserUtils.getEmailFromAccessUser();
        final UserAuthority userAuthority = userAuthorityQueryService.findByEmail(email);
        return new UserAuthorityInfoResponse(userAuthority.getAuthority());
    }
}
