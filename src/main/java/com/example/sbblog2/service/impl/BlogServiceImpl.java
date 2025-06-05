package com.example.sbblog2.service.impl;

import com.example.sbblog2.entity.Blog;
import com.example.sbblog2.dto.BlogDTO;
import com.example.sbblog2.dao.BlogRepository;
import com.example.sbblog2.service.BlogService;
import com.example.sbblog2.util.UserUtils;
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
        blog.setUser(UserUtils.getCurrentUser());

        // 存入数据库
        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        // 如果当前登录用户是 admin，查询所有
        if (UserUtils.isAdmin()){
            return blogRepository.findAll(pageable);
        } else {
            // 如果不是 admin 则查询属于自己的博客
            return blogRepository.findBlogsByUserId(UserUtils.getCurrentUser().getId(), pageable);
        }
    }

    @Override
    public Page<Blog> searchAllByTitleContains(String keyword, Pageable pageable) {
        if (UserUtils.isAdmin()){
            return blogRepository.searchAllByTitleContains(keyword, pageable);
        } else {
            return blogRepository.findBlogsByUserIdAndTitleContains(UserUtils.getCurrentUser().getId(),keyword, pageable);
        }
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

