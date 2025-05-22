package com.example.sbblog2.dao;

import com.example.sbblog2.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void xxx(){

        User user = new User();
        String name = "测试2 - 查询";
        user.setName(name);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEnabled(true);

        userRepository.save(user);

        // 通过 UserRepository 查询名称为 "XXX" 是否存在于数据库中
        Optional<User> ou = userRepository.findFirstByName(name);
        boolean present = ou.isPresent();
        Assertions.assertTrue(present);

        // 通过测试后 删除测试数据
        userRepository.delete(ou.get());
    }
}
