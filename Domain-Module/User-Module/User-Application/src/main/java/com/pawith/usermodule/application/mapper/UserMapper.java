package com.pawith.usermodule.application.mapper;

import com.pawith.commonmodule.annotation.Mapper;
import com.pawith.usermodule.application.handler.event.UserSignUpEvent;
import com.pawith.usermodule.domain.entity.User;

@Mapper
public class UserMapper {

    public static User toEntity(UserSignUpEvent userSignUpEvent){
        return User.builder()
                .nickname(userSignUpEvent.getNickname())
                .email(userSignUpEvent.getEmail())
                .provider(userSignUpEvent.getProvider())
                .build();
    }

}
