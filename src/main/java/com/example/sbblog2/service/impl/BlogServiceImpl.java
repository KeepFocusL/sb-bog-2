package com.example.sbblog2.service.impl;

import com.example.sbblog2.Blog;
import com.example.sbblog2.BlogDTO;
import com.example.sbblog2.BlogRepository;
import com.example.sbblog2.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Override
    public Blog save(BlogDTO blogDTO) {
        // 手动把 BlogDTO 转为 Blog
        Blog blog = new Blog();

        if (blogDTO.getId() != null) {
            blog = blogRepository.findById(blogDTO.getId()).get();
            blog.setUpdated_at(LocalDateTime.now());
        } else {
            blog.setCreated_at(LocalDateTime.now());
        }

        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        blog.setDescription(blogDTO.getDescription());
        blog.setCover(blogDTO.getCover());

        // 存入数据库
        return blogRepository.save(blog);
    }
}
