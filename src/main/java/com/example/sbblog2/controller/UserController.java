package com.example.sbblog2.controller;

import com.example.sbblog2.dto.PasswordResetEmailDTO;
import com.example.sbblog2.dto.UserDTO;
import com.example.sbblog2.entity.User;
import com.example.sbblog2.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("user",new UserDTO());
        return "user/register";
    }

    @PostMapping("register")
    public String register(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, HttpServletRequest httpServletRequest) throws ServletException {
        // 根据邮箱查询数据库
        User existingUser =  userService.findByEmail(userDTO.getEmail());
        if (existingUser != null){
            result.rejectValue("email", "exist", "该邮箱已被注册");
        }

        if (result.hasErrors()){
            return "user/register";
        }

        userService.save(userDTO);

        // 帮该用户自动登录
        httpServletRequest.login(userDTO.getEmail(), userDTO.getPassword());
        return "redirect:/";
    }

    @GetMapping("password-reset")
    public String showPasswordResetForm(Model model){
        model.addAttribute("passwordResetEmail", new PasswordResetEmailDTO());
        return "user/password-reset";
    }

    @PostMapping("password-reset")
    public String passwordReset(
            @Valid @ModelAttribute("passwordResetEmail") PasswordResetEmailDTO passwordResetEmailDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ){
        // 检查邮箱是否存在
        User existingUser = userService.findByEmail(passwordResetEmailDTO.getEmail());
        if (existingUser == null){
            result.rejectValue("email", "non-existent", "该邮箱不存在");
        }

        if (result.hasErrors()){
            return "user/password-reset";
        }

        // todo: 发送邮件

        redirectAttributes.addFlashAttribute("success", "密码重置邮件已发送，请注意查收");

        return "redirect:/user/password-reset";
    }
}
