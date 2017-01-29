package com.skwarek.blog.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Michal on 04.10.2016.
 */
@RestController
public class LoginController {

    @RequestMapping("/user")
    public Principal user(Principal user) {

        return user;
    }
}

