package com.example.sbblog2.controller;

import com.example.sbblog2.dto.UserDto;
import com.example.sbblog2.entity.User;
import com.example.sbblog2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String enroll(Model model){
        model.addAttribute("user",new UserDto());
        return "user/register";
    }

    @PostMapping("register")
    public String register(@Valid @ModelAttribute("user") UserDto userDTO, BindingResult result){
        // 根据邮箱查询数据库
        User existingUser =  userService.findByEmail(userDTO.getEmail());
        if (existingUser != null){
            result.rejectValue("email", "exist", "该邮箱已被注册");
        }

        if (result.hasErrors()){
            return "user/register";
        }
        userService.save();
        return "redirect:/";
    }
}