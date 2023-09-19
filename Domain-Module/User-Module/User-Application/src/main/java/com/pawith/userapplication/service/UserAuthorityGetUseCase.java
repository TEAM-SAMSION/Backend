package com.pawith.userapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.userapplication.dto.response.UserAuthorityInfoResponse;
import com.pawith.userdomain.entity.UserAuthority;
import com.pawith.userdomain.service.UserAuthorityQueryService;
import com.pawith.userdomain.utils.UserUtils;
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
