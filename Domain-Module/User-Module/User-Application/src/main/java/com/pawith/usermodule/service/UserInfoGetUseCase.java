package com.pawith.usermodule.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.usermodule.dto.response.UserInfoResponse;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoGetUseCase {

    public UserInfoResponse getUserInfo(){
        final User user = UserUtils.getAccessUser();
        return new UserInfoResponse(user.getNickname(), user.getEmail(), user.getImageUrl());
    }
}
