package com.robcorp.oauth2practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;

import static java.util.Arrays.asList;

@Configuration
//@EnableOAuth2Sso -> this is what we're setting up oursevles, effectively
@EnableOAuth2Client
@Profile("manual")
public class ManualSingleProviderSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oAuth2ClientContext;
    @Autowired
    OAuth2ClientContextFilter oAuth2ClientContextFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .antMatcher("/**")
                .authorizeRequests()
            .antMatchers(
                    "/",
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
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
                //add our oauth2clientauthprocessingfilter before security auth filters start (basicauth is early in chain?)
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                //required to handle redirects during oauth flow, registered automatically by @enableoauth2sso but required w/ this more manual setup
                .addFilterBefore(oAuth2ClientContextFilter, SecurityContextPersistenceFilter.class);
        //@formatter:on
    }

    @Bean
    @ConfigurationProperties("facebook.client")
    public AuthorizationCodeResourceDetails facebookAuthServer() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    public ResourceServerProperties facebookResourceServer() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("github.client")
    public AuthorizationCodeResourceDetails githubAuthServer() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("github.resource")
    public ResourceServerProperties githubResourceServer() {
        return new ResourceServerProperties();
    }

    private Filter ssoFilter() {
        CompositeFilter ssoCompositeFilter = new CompositeFilter();

        ssoCompositeFilter.setFilters(asList(
                createOAuthFilter(
                        "/login/facebook",
                        facebookAuthServer(),
                        facebookResourceServer()
                ), createOAuthFilter(
                        "/login/github",
                        githubAuthServer(),
                        githubResourceServer()
                )));
        return ssoCompositeFilter;
    }

    private Filter createOAuthFilter(String appliedPath,
                                     AuthorizationCodeResourceDetails authCodeResourceDetails,
                                     ResourceServerProperties resourceServerProperties) {

        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(appliedPath);

        OAuth2RestTemplate template = new OAuth2RestTemplate(authCodeResourceDetails, oAuth2ClientContext);

        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                resourceServerProperties.getUserInfoUri(),
                resourceServerProperties.getClientId());

        filter.setRestTemplate(template);
        filter.setTokenServices(tokenServices);
        return filter;
    }
}
