package com.example.sbblog2.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PasswordResetDTO {
    @NotEmpty
    String token;

    @Size(min = 6, max = 15)
    String password;

    @Size(min = 6, max = 15)
    String confirmPassword;
}
