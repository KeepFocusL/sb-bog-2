package com.example.sbblog2.service.impl;

import com.example.sbblog2.Blog;
import com.example.sbblog2.BlogDTO;
import com.example.sbblog2.service.BlogService;
import org.springframework.stereotype.Service;

//@Service
public class BlogServiceSoutImpl implements BlogService {
    @Override
    public Blog save(BlogDTO blogDTO) {
        System.out.println(blogDTO);
        return null;
    }
}
