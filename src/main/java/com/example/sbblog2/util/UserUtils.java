package com.example.sbblog2.util;

import com.example.sbblog2.bean.SecurityUser;
import com.example.sbblog2.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static SecurityUser getCurrentSecurityUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return (SecurityUser) authentication.getPrincipal();
        }
        return null;
    }

    public static User getCurrentUser() {
        SecurityUser currentSecurityUser = getCurrentSecurityUser();
        return currentSecurityUser == null ? null : currentSecurityUser.getUser();
    }

    public static Boolean isAdmin() {
        return getCurrentSecurityUser().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_admin"));
    }

    public static Boolean isEditor() {
        return getCurrentSecurityUser().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_editor"));
    }
}
