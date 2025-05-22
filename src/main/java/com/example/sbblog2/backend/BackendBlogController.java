package com.example.sbblog2.backend;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import com.example.sbblog2.entity.Blog;
import com.example.sbblog2.dto.BlogDTO;
import com.example.sbblog2.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("backend/blog")
public class BackendBlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("blog", new Blog());
        return "backend/blog/add";
    }

    @Value("${custom.upload.base-path}")
    String uploadBasePath;
    @Value("${custom.upload.cover-path}")
    String coverPath;

    @PostMapping("add")
    public String save(@RequestParam(value = "coverImage", required = false) MultipartFile file, @Valid @ModelAttribute("blog") BlogDTO blog, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "/backend/blog/add";
        }

        uploadCover(file, blog);

        blogService.save(blog);

        System.out.println(blog);

        return "redirect:/backend/blog";
    }

    private void uploadCover(MultipartFile file, BlogDTO blog) throws IOException {
        if (file != null && !file.isEmpty()) {
            File dir = new File(uploadBasePath + File.separator + coverPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            System.out.println(originalFilename);
            assert originalFilename != null;
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID() + suffix;

            file.transferTo(new File(dir.getAbsolutePath() + File.separator + newFilename));
            blog.setCover("/" + coverPath + File.separator + newFilename);
        }
    }

    @GetMapping("")
    public String list(Model model,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size,
                       @RequestParam(value = "keyword", required = false) String keyword) {
        // 如果没有则赋值为 orElse 的值
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());

        Page<Blog> pageContent;
        if (keyword == null){
            pageContent = blogService.findAll(pageable);
        }else {
            // 如果搜索条件不为空，传值回前端
            model.addAttribute("keyword", keyword);
            pageContent = blogService.searchAllByTitleContains(keyword, pageable);
        }

        model.addAttribute("page", pageContent);
        model.addAttribute("requestURI", "/backend/blog");

        return "backend/blog/list";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        blogService.deleteById(id);
        return "redirect:/backend/blog";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable Long id , Model model) {
        Optional<Blog> optionalBlog = blogService.findById(id);
        if(optionalBlog.isEmpty()){
            throw new EntityNotFoundException();
        }else {
            Blog blog = optionalBlog.get();
            model.addAttribute("blog", blog);
        }
        return "backend/blog/edit";
    }

    @PostMapping("update")
    public String update(@RequestParam(value = "coverImage", required = false) MultipartFile file, @Valid @ModelAttribute("blog") BlogDTO blog, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "/backend/blog/edit";
        }

        uploadCover(file, blog);

        blogService.save(blog);

        System.out.println(blog);

        return "redirect:/backend/blog";
    }

}
