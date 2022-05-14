package com.lee.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 * 这个本质是filter，会进行链式调用
 * 在父类会调用doFilterInternal方法
 */
public class DemoAuthFilter extends OncePerRequestFilter {

    //认证失败的处理器
    private AuthenticationFailureHandler failureHandler = new AuthFailureHandler();

    private static final String AUTHORIZATION_HEAD = "token";
    // 判断请求头中的参数工具类
    private RequestMatcher requiresAuthenticationRequestMatcher = new RequestHeaderRequestMatcher(AUTHORIZATION_HEAD);

    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        /**
         * 处理掉不需要过滤的
         */
        if (!requiresAuthenticationRequestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        AuthenticationException failed = null;

        String token = request.getHeader(AUTHORIZATION_HEAD);
        String[] parts = token.split(",");
        DemoToken demoToken = new DemoToken(parts[0], parts[1]);
        Authentication authenticate = authenticationManager.authenticate(demoToken);
        if (authenticate.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            filterChain.doFilter(request, response);
        } else {
            //认证失败了
            SecurityContextHolder.clearContext();
            failureHandler.onAuthenticationFailure(request, response, failed);
        }

    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(failureHandler, "AuthenticationFailureHandler must be specified");
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        logger.info("设置了authenticationManager");
        this.authenticationManager = authenticationManager;
    }

}
