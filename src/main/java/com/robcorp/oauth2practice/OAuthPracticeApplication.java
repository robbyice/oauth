package com.robcorp.oauth2practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
public class OAuthPracticeApplication {
    public static void main(String [] args) {
        SpringApplication.run(OAuthPracticeApplication.class, args);
    }
}
