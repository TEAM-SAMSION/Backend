package com.pawith.userapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.userapplication.dto.request.UserNicknameChangeRequest;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.entity.UserAuthority;
import com.pawith.userdomain.service.UserAuthorityQueryService;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class UserNicknameChangeUseCase {

    private final UserUtils userUtils;
    private final UserAuthorityQueryService userAuthorityQueryService;

    public void changeUserName(final UserNicknameChangeRequest request){
        final User user = userUtils.getAccessUser();
        user.updateNickname(request.getNickname());
        final UserAuthority userAuthority = userAuthorityQueryService.findByUserId(user.getId());
        userAuthority.changeUserAuthority();
    }
}
