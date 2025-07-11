package com.example.sbblog2.backend;

import com.example.sbblog2.entity.User;
import com.example.sbblog2.util.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("backend/blog")
@PreAuthorize("isAuthenticated()")
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

        return "backend/blog/list";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        Blog blogFromDB = blogService.findById(id).get();
        if (!isOwnedByUserOrIsAdmin(blogFromDB)){
            throw new RuntimeException("异常操作");
        }

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

            // 安全校验 - 检查当前登录用户是不是这篇文章的作者
            if (!isOwnedByUserOrIsAdmin(blog)){
                throw new RuntimeException("异常操作");
            }

            model.addAttribute("blog", blog);
        }
        return "backend/blog/edit";
    }

    @PostMapping("update")
    public String update(@RequestParam(value = "coverImage", required = false) MultipartFile file, @Valid @ModelAttribute("blog") BlogDTO blog, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "/backend/blog/edit";
        }

        Blog blogFromDB = blogService.findById(blog.getId()).get();

        if (!isOwnedByUserOrIsAdmin(blogFromDB)){
            throw new RuntimeException("异常操作 - POST");
        }

        uploadCover(file, blog);

        blogService.save(blog);

        System.out.println(blog);

        return "redirect:/backend/blog";
    }

    // 安全校验 - 检查当前登录用户是不是这篇文章的作者
    private boolean isOwnedByUserOrIsAdmin(Blog blogFromDB) {
        User currentUser = UserUtils.getCurrentUser();
        return Objects.equals(blogFromDB.getUser().getId(), currentUser.getId()) || UserUtils.isAdmin();
    }
}
