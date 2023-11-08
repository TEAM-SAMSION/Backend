package com.pawith.userapplication.mapper;

import com.pawith.commonmodule.annotation.Mapper;
import com.pawith.commonmodule.event.UserSignUpEvent;
import com.pawith.userdomain.entity.User;

@Mapper
public class UserMapper {

    public static User toUserEntity(UserSignUpEvent userSignUpEvent, String imageUrl) {
        return User.builder()
            .nickname(userSignUpEvent.getNickname())
            .email(userSignUpEvent.getEmail())
            .provider(userSignUpEvent.getProvider())
            .imageUrl(imageUrl)
            .build();
    }

}
