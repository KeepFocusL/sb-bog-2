package com.example.sbblog2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @GetMapping("dashboard")
    public String dashboard(){
        return "user/dashboard";
    }
}
