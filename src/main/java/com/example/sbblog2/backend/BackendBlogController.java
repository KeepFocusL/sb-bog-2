package com.example.sbblog2.backend;

import com.example.sbblog2.BlogRepository;
import com.example.sbblog2.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("backend/blog")
public class BackendBlogController {

    @Autowired
    BlogRepository blogRepository;

    @GetMapping("add")
    public String add() {
        return "backend/blog/add";
    }

    @PostMapping("add")
    public String save(Blog blog) {
        System.out.println(blog);
        blogRepository.save(blog);
        return "redirect:/backend/blog";
    }

    @GetMapping("")
    public String list(Model model) {
        List<Blog> blogs = blogRepository.findAll();
        model.addAttribute("blogs", blogs);
        return "backend/blog/list";
    }


}
