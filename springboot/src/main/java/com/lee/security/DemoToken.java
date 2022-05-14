package com.lee.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class DemoToken extends AbstractAuthenticationToken {

    private String userName;

    private String password;

    public DemoToken(String userName, String password) {
        super(Collections.emptyList());
        this.password = password;
        this.userName = userName;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return userName;
    }
}
