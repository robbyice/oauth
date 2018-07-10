package com.robcorp.oauth2practice;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
@Profile("basic")
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .antMatcher("/")
                    .authorizeRequests()
                        .antMatchers(
                            "/login**",
                            "/webjars/**",
                            "/error**")
                            .permitAll()
                        .anyRequest()
                            .authenticated()
                    .and()
                        .logout()
                            .logoutSuccessUrl("/")
                                .permitAll();
        //@formatter:on
    }
}
