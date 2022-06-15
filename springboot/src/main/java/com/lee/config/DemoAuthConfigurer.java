package com.lee.config;

import com.lee.security.DemoAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableWebSecurity
public class DemoAuthConfigurer extends WebSecurityConfigurerAdapter {

    @Bean
    AuthenticationProvider demoAuthProvider() {
        return new DemoAuthProvider();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(demoAuthProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/v2/api-docs",
                "/**/swagger-resources/configuration/ui",
                "/**/swagger-resources",
                "/**/swagger-resources/configuration/security",
                "/images/**",
                "/**/swagger-ui.html",
                "/**/webjars/**",
                "/**/favicon.ico",
                "/**/css/**",
                "/**/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/**/v2/api-docs",
                        "/**/swagger-resources/configuration/ui",
                        "/**/swagger-resources",
                        "/**/swagger-resources/configuration/security",
                        "/images/**",
                        "/**/swagger-ui.html",
                        "/**/webjars/**",
                        "/**/favicon.ico",
                        "/**/css/**",
                        "/**/js/**"
                )
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new com.lee.security.DemoAuthConfigurer<>())
                .and()
                .sessionManagement().disable();


    }
}
