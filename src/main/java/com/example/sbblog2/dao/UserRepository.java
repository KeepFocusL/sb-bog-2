package com.example.sbblog2.dao;

import com.example.sbblog2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByName(String name);

    Optional<User> findFirstByEmail(String email);
}