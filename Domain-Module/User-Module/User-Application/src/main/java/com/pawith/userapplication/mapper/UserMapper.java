package com.pawith.userapplication.mapper;

import com.pawith.commonmodule.annotation.Mapper;
import com.pawith.userapplication.handler.event.UserSignUpEvent;
import com.pawith.userdomain.entity.User;

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
