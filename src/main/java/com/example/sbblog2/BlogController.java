package com.example.sbblog2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("blog")
public class BlogController {

    @GetMapping("")
    public String list() {
        return "blog/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable String id) {
        System.out.println("id = " + id);
        return "blog/show";
    }
}
