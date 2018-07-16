package com.robcorp.oauth2practice;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@Profile("manual-multiple")
@EnableOAuth2Client
public class ManualMultipleProviderSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http.antMatcher("/**")
                .authorizeRequests()
                    .antMatchers("/login**",
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
