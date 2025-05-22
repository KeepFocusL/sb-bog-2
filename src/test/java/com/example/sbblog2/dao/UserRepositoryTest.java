package com.example.sbblog2.dao;

import com.example.sbblog2.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void xxx(){

        User user = new User();
        user.setName("测试");
        user.setPassword("123456");
        user.setEnabled(true);

        userRepository.save(user);
    }
}
