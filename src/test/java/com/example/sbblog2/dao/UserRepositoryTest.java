package com.example.sbblog2.dao;

import com.example.sbblog2.entity.Permission;
import com.example.sbblog2.entity.Role;
import com.example.sbblog2.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    @Test
    void rbac(){
        // admin 用户具有 admin 角色
        // admin 角色具有两个权限
        String roleName = "admin";
        Optional<User> ou = userRepository.findFirstByName(roleName);
        Assertions.assertTrue(ou.isPresent());

        User user = ou.get();
        Set<Role> roles = user.getRoles();
        int actual = roles.size();
        Assertions.assertEquals(1, actual);

        Role role = roles.iterator().next();
        String actualName = role.getName();
        Assertions.assertEquals(roleName, actualName);

        // 测试角色跟权限的关系
        Set<Permission> permissions = role.getPermissions();
        Assertions.assertEquals(3, permissions.size());

        Set<String> permissionNames = new HashSet<>();
        for (Permission permission : permissions) {
            permissionNames.add(permission.getName());
        }
        Assertions.assertTrue(permissionNames.contains("/backend"));
        Assertions.assertTrue(permissionNames.contains("/backend/blog"));
        Assertions.assertTrue(permissionNames.contains("/backend/user"));
    }
}
