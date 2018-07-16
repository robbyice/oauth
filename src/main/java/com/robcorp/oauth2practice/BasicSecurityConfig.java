package com.robcorp.oauth2practice;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
@EnableOAuth2Sso
@Profile("basic")
@Order(HIGHEST_PRECEDENCE)
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .antMatcher("/**")
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
                                .permitAll()
                    .and()
                        .csrf()
                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        //@formatter:on
    }
}
