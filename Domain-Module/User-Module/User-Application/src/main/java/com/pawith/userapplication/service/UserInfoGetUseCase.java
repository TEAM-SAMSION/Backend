package com.pawith.userapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.userapplication.dto.response.UserInfoResponse;
import com.pawith.userapplication.dto.response.UserJoinTermResponse;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoGetUseCase {

    private final UserUtils userUtils;

    public UserInfoResponse getUserInfo(){
        final User user = userUtils.getAccessUser();
        return new UserInfoResponse(user.getNickname(), user.getEmail(), user.getImageUrl());
    }

    public UserJoinTermResponse getTerm() {
        final User user = userUtils.getAccessUser();
        return new UserJoinTermResponse(user.getJoinTerm());
    }
}
