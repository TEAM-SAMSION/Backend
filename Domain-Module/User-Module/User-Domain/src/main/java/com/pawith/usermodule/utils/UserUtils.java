package com.pawith.usermodule.utils;

import com.pawith.commonmodule.util.SecurityUtils;
import com.pawith.usermodule.entity.User;
import com.pawith.usermodule.service.UserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
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
