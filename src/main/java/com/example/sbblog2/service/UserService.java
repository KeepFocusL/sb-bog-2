package com.example.sbblog2.service;

import com.example.sbblog2.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void save();

    User findByEmail(@Email String email);

}
