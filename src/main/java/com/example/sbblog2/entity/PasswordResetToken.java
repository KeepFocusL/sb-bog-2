package com.example.sbblog2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String token;

    @Column(nullable = false)
    LocalDateTime expirationDate;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    User user;
}
