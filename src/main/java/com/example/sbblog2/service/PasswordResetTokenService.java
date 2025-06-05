package com.example.sbblog2.service;

import com.example.sbblog2.entity.PasswordResetToken;

public interface PasswordResetTokenService {
    PasswordResetToken findFirstByToken(String token);

    PasswordResetToken save(PasswordResetToken passwordResetToken);
}
