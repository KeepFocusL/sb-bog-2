package com.example.sbblog2.controller;

import com.example.sbblog2.entity.Blog;
import com.example.sbblog2.dao.BlogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("blog")
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @GetMapping("")
    public String list(Model model,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size){
        //如果没有赋值为orElse的值,为第一页
        int currentPage = page.orElse(1);
        //五条一页
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());

        Page<Blog> pageContent = blogRepository.findAll(pageable);

        model.addAttribute("page",pageContent);
        return "blog/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model){
        Optional<Blog> optionBlog = blogRepository.findById(id);

        if (optionBlog.isEmpty()){
            throw new EntityNotFoundException();
        }else {
            model.addAttribute("blog",optionBlog.get() );
            return "blog/show";
        }
    }
}
