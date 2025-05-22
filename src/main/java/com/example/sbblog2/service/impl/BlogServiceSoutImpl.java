package com.example.sbblog2.service.impl;

import com.example.sbblog2.entity.Blog;
import com.example.sbblog2.dto.BlogDTO;
import com.example.sbblog2.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

//@Service
public class BlogServiceSoutImpl implements BlogService {
    @Override
    public Blog save(BlogDTO blogDTO) {
        System.out.println(blogDTO);
        return null;
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Blog> searchAllByTitleContains(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Blog> findById(Long id) {
        return Optional.empty();
    }
}
