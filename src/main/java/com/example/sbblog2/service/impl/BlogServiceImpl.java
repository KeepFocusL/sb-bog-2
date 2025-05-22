package com.example.sbblog2.service.impl;

import com.example.sbblog2.Blog;
import com.example.sbblog2.BlogDTO;
import com.example.sbblog2.BlogRepository;
import com.example.sbblog2.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> searchAllByTitleContains(String keyword, Pageable pageable) {
        return blogRepository.searchAllByTitleContains(keyword, pageable);
    }

    @Override
    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }
}
