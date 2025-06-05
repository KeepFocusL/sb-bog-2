package com.example.sbblog2.dao;

import com.example.sbblog2.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findFirstByToken(String token);

    @Modifying
    @Query(value = "update PasswordResetToken prt set prt.expirationDate=CURRENT_TIMESTAMP() where prt.token = :token")
    void updateExpirationDateForThisToken(@Param(value = "token") String token);
}
