package com.example.sbblog2.backend;

import com.example.sbblog2.entity.User;
import com.example.sbblog2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("backend/user")
public class BackendUserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public String list(Model model,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<User> users = userService.findAll(currentPage, pageSize);
        // 拿到数据后返回给前端
        model.addAttribute("page", users);
        // 前端拿到数据后按照需要的方式展示数据
        return "backend/user/list";
    }
}
