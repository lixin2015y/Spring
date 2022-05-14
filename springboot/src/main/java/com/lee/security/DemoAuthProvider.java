package com.lee.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.HashMap;
import java.util.Map;

public class DemoAuthProvider implements AuthenticationProvider {

    public DemoAuthProvider() {

    }

    private static Map<String, String> map = new HashMap<>();

    static {
        map.put("lixin", "123");
        map.put("zhangsan", "456");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DemoToken demoToken = (DemoToken) authentication;
        boolean result = demoToken.getCredentials().equals(map.get(demoToken.getPrincipal()));
        if (!result) {
            throw new BadCredentialsException("鉴权失败");
        }
        authentication.setAuthenticated(result);
        return authentication;
    }

    // 这里是判断当前凭证是否可以被当前provider认证
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(DemoToken.class);
    }
}
