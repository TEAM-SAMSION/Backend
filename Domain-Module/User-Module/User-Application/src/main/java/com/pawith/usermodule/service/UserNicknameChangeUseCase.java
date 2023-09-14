package com.pawith.usermodule.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.usermodule.service.dto.UserNicknameChangeRequest;
import com.pawith.usermodule.entity.Authority;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.entity.UserAuthority;
import com.pawith.usermodule.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class UserNicknameChangeUseCase {

    private final UserAuthorityQueryService userAuthorityQueryService;

    public void changeUserName(final UserNicknameChangeRequest request){
        final User user = UserUtils.getAccessUser();
        user.updateNickname(request.getNickname());
        final UserAuthority userAuthority = userAuthorityQueryService.findByEmail(user.getEmail());
        if(userAuthority.getAuthority().equals(Authority.GUEST)){
            userAuthority.changeUserAuthority();
        }
    }
}
