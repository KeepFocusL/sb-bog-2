package com.example.sbblog2.backend;

import com.example.sbblog2.entity.User;
import com.example.sbblog2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("backend/user")
public class BackendUserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public String list(Model model){
        List<User> users = userService.findAll();
        // 拿到数据后返回给前端
        model.addAttribute("users", users);
        // 前端拿到数据后按照需要的方式展示数据
        return "backend/user/list";
    }
}
