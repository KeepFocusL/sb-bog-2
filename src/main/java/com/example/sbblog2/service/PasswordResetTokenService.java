package com.example.sbblog2.service;

import com.example.sbblog2.entity.PasswordResetToken;
import org.springframework.transaction.annotation.Transactional;

public interface PasswordResetTokenService {
    PasswordResetToken findFirstByToken(String token);

    PasswordResetToken save(PasswordResetToken passwordResetToken);

    @Transactional
    void expireThisToken(String token);
}
