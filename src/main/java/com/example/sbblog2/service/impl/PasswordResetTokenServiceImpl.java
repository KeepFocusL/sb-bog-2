package com.example.sbblog2.service.impl;

import com.example.sbblog2.dao.PasswordResetTokenRepository;
import com.example.sbblog2.entity.PasswordResetToken;
import com.example.sbblog2.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken findFirstByToken(String token) {
        return passwordResetTokenRepository.findFirstByToken(token).orElse(null);
    }

    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public void expireThisToken(String token) {
        passwordResetTokenRepository.updateExpirationDateForThisToken(token);
    }
}
