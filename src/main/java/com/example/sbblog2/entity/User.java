package com.example.sbblog2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

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
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName="id")
    )
    Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    List<Blog> blogs = new ArrayList<>();

    public Set<Permission> getPermissions() {
        List<Permission> allPermissions = new ArrayList<>();
        for (Role role : this.roles) {
            allPermissions.addAll(role.getPermissions());
        }
        allPermissions.sort(Comparator.comparingInt(Permission::getSort));
        return new LinkedHashSet<>(allPermissions);
    }
}
