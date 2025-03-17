package com.example.sbblog2.backend;

import jakarta.persistence.EntityNotFoundException;
import com.example.sbblog2.Blog;
import com.example.sbblog2.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        blogRepository.deleteById(id);
        return "redirect:/backend/blog";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable Long id , Model model) {
        Optional<Blog> optionalBlog = blogRepository.findById(id);
        if(optionalBlog.isEmpty()){
            throw new EntityNotFoundException();
        }else {
            Blog blog = optionalBlog.get();
            model.addAttribute("blog", blog);
        }
        return "backend/blog/edit";
    }

}
