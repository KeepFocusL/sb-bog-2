package com.example.sbblog2.backend;

import jakarta.persistence.EntityNotFoundException;
import com.example.sbblog2.Blog;
import com.example.sbblog2.BlogDTO;
import com.example.sbblog2.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("backend/blog")
public class BackendBlogController {

    @Autowired
    BlogRepository blogRepository;

    @GetMapping("add")
    public String add() {
        return "backend/blog/add";
    }

    @Value("${custom.upload.base-path}")
    String uploadBasePath;
    @Value("${custom.upload.cover-path}")
    String coverPath;
    @PostMapping("add")
    public String save(@RequestParam(value = "coverImage", required = false) MultipartFile file, BlogDTO blog, Model model) throws IOException {
//        uploadCover(file, blog);

//        blogRepository.save(blog);

        System.out.println(blog);

        if (blog.getTitle().length()<5){
            model.addAttribute("tittle-error", "标题长度不能小于 5");
        }

        return "/backend/blog/add";
    }

    private void uploadCover(MultipartFile file, Blog blog) throws IOException {
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
            pageContent = blogRepository.findAll(pageable);
        }else {
            // 如果搜索条件不为空，传值回前端
            model.addAttribute("keyword", keyword);
            pageContent = blogRepository.searchAllByTitleContains(keyword, pageable);
        }

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
    public String update(@RequestParam(value = "coverImage", required = false) MultipartFile file, Blog blog) throws IOException {
        uploadCover(file, blog);

        blogRepository.save(blog);

        return "redirect:/backend/blog";
    }

}
