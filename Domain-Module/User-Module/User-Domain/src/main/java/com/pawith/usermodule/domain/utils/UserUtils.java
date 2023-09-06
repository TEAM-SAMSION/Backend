package com.pawith.usermodule.domain.utils;

import com.pawith.usermodule.domain.entity.User;
import com.pawith.usermodule.domain.service.UserQueryService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {

    private static UserQueryService userQueryService;

    public UserUtils(UserQueryService userQueryService) {
        UserUtils.userQueryService = userQueryService;
    }

    public static User getAccessUser(){
        final String email = getCurrentUserEmail();
        return userQueryService.findByEmail(email);
    }

    private static String getCurrentUserEmail() {
        final String email = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return email;
    }

    public static String getEmailFromAccessUser(){
        return getCurrentUserEmail();
    }
}
