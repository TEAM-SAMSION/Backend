package com.pawith.userdomain.utils;

import com.pawith.commonmodule.util.SecurityUtils;
import com.pawith.userdomain.service.UserQueryService;
import com.pawith.userdomain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserQueryService userQueryService;

    public User getAccessUser(){
        final String email = SecurityUtils.getAuthenticationPrincipal();
        return userQueryService.findByEmail(email);
    }

    public static String getEmailFromAccessUser(){
        return SecurityUtils.getAuthenticationPrincipal();
    }
}
