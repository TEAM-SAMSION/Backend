package com.pawith.authmodule.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;


public class JWTAuthenticationToken extends AbstractAuthenticationToken {
    private String email;
    public JWTAuthenticationToken(String email) {
        super(null);
        this.email=email;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }
}
