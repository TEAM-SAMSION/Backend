package com.pawith.commonmodule.security;

import com.navercorp.fixturemonkey.FixtureMonkey;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public final class WithMockAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithMockAuthUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockAuthUser annotation) {
        final SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        emptyContext.setAuthentication(new AbstractAuthenticationToken(null) {
            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return annotation.email().equals("email") ? FixtureMonkey.create().giveMeOne(String.class):annotation.email();
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }
        });
        return emptyContext;
    }
}
