package com.example.sbblog2.dao;

import com.example.sbblog2.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void xxx(){

        User user = new User();
        user.setName("测试 - 密码加密");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEnabled(true);

        userRepository.save(user);
    }
}
