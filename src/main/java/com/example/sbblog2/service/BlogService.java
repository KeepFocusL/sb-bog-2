package com.example.sbblog2.service;

import com.example.sbblog2.Blog;
import com.example.sbblog2.BlogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BlogService {
    Blog save(BlogDTO blogDTO);

    Page<Blog> findAll(Pageable pageable);

    Page<Blog> searchAllByTitleContains(String keyword, Pageable pageable);

    void deleteById(Long id);

    Optional<Blog> findById(Long id);
}
