package com.pawith.commonmodule.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    public static String getAuthenticationPrincipal() {
        final String email = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return email;
    }
}
