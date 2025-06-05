package com.example.sbblog2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    private String cover;

    @Nullable
    String description;

    LocalDateTime created_at;
    @Nullable
    LocalDateTime updated_at;

    @ManyToOne
    // @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
}
