package com.example.sbblog2.dao;


import com.example.sbblog2.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> searchAllByTitleContains(String title, Pageable pageable);

    Page<Blog> findBlogsByUserId(Long id, Pageable pageable);

    Page<Blog> findBlogsByUserIdAndTitleContains(Long id, String keyword, Pageable pageable);
}


