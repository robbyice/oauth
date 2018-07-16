package com.robcorp.oauth2practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Value("${spring.profiles.active}")
    String mode;

    @Value("${spring.thymeleaf.enabled")
    String enabled;

    @RequestMapping("/")
    public ModelAndView homePage() {
        ModelAndView index = new ModelAndView("index");
        index.addObject("mode", mode);
        return index;
    }

    @RequestMapping("/user")
    public Principal getUser(Principal principal) {
        return principal;
    }

    @RequestMapping("/test")
    public ModelAndView test() {
        ModelAndView test = new ModelAndView("test");
        return test;
    }
}
