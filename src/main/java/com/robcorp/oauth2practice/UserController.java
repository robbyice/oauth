package com.robcorp.oauth2practice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @RequestMapping("/user")
    public Principal getUser(Principal principal) {
        return principal;
    }

    @RequestMapping("/test")
    public String test() {
        return "foobar";
    }
}