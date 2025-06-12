package com.example.sbblog2.service.impl;

import com.example.sbblog2.dao.RoleRepository;
import com.example.sbblog2.dao.UserRepository;
import com.example.sbblog2.dto.UserDTO;
import com.example.sbblog2.entity.Role;
import com.example.sbblog2.entity.User;
import com.example.sbblog2.service.UserService;
import com.example.sbblog2.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void save(UserDTO userDTO) {
        System.out.println("UserServiceImpl.save");

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> firstByEmail = userRepository.findFirstByEmail(email);
        return firstByEmail.orElse(null);
    }

    @Override
    public void updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public Page<User> findAll(Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());
        return userRepository.findAll(pageable);
    }

    @Override
    public void assignRole(String roleName) {
        User user = UserUtils.getCurrentUser();
        // 根据 roleName 找到 Role
        Role role = roleRepository.findFirstByName(roleName);
        // 给当前用户分配角色
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
