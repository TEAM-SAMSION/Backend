package com.pawith.usermodule.utils;

import com.pawith.commonmodule.util.SecurityUtils;
import com.pawith.usermodule.domain.entity.User;
import com.pawith.usermodule.domain.service.UserQueryService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {

    private static UserQueryService userQueryService;

    public UserUtils(UserQueryService userQueryService) {
        UserUtils.userQueryService = userQueryService;
    }

    public static User getAccessUser(){
        final String email = SecurityUtils.getAuthenticationPrincipal();
        return userQueryService.findByEmail(email);
    }

    public static String getEmailFromAccessUser(){
        return SecurityUtils.getAuthenticationPrincipal();
    }
}
