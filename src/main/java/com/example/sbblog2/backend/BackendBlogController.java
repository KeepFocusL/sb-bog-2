package com.example.sbblog2.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("backend/blog")
public class BackendBlogController {

    @GetMapping("add")
    public String add(){
        return "backend/blog/add";
    }

    @PostMapping("add")
    public String save(){
        System.out.println("接收到博客信息");
        return "redirect:/backend";
    }


}
