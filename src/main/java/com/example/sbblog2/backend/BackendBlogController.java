package com.example.sbblog2.backend;

import jakarta.persistence.EntityNotFoundException;
import com.example.sbblog2.Blog;
import com.example.sbblog2.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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
    public String list(Model model,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size) {
        // 如果没有则赋值为 orElse 的值
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());

        Page<Blog> pageContent = blogRepository.findAll(pageable);

        model.addAttribute("page", pageContent);

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

    @PostMapping("update")
    public String update(Blog blog) {
        blogRepository.save(blog);
        return "redirect:/backend/blog";
    }

}
