package com.example.sbblog2.service;

import com.example.sbblog2.dto.UserDTO;
import com.example.sbblog2.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void save(UserDTO userDTO);

    User findByEmail(@Email String email);

    void updatePassword(User user);

    Page<User> findAll(Integer currentPage, Integer pageSize);

    void assignRole(String roleName);
}
