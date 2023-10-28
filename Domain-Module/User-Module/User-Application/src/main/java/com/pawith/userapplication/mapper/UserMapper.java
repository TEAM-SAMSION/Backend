package com.pawith.userapplication.mapper;

import com.pawith.commonmodule.annotation.Mapper;
import com.pawith.commonmodule.event.UserSignUpEvent;
import com.pawith.userapplication.dto.request.PathHistoryCreateRequest;
import com.pawith.userdomain.entity.PathHistory;
import com.pawith.userdomain.entity.User;

@Mapper
public class UserMapper {

    public static User toUserEntity(UserSignUpEvent userSignUpEvent){
        return User.builder()
                .nickname(userSignUpEvent.getNickname())
                .email(userSignUpEvent.getEmail())
                .provider(userSignUpEvent.getProvider())
                .build();
    }

}
