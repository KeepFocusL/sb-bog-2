package com.example.sbblog2.controller;

import com.example.sbblog2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("dashboard")
    @PreAuthorize("isAuthenticated()")
    public String dashboard(){
        return "user/dashboard";
    }

    @GetMapping("register")
    public String enroll(){
        return "user/register";
    }

    @PostMapping("register")
    public String register(){
        userService.save();
        return "redirect:/";
    }
}