package com.pawith.usermodule.mapper;

import com.pawith.commonmodule.annotation.Mapper;
import com.pawith.usermodule.handler.event.UserSignUpEvent;
import com.pawith.usermodule.entity.User;

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
