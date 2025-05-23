package com.example.sbblog2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private String password;
    private String email;
    private String mobile;
    private LocalDateTime createdAt;
    private boolean enabled;

    @ManyToMany
    Set<Role> roles = new HashSet<>();
}
