package com.example.sbblog2.controller;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDTO {
    @Size(min = 6, max = 20, message = "(当前密码)长度必须在6到20位之间")
    private String currentPassword;

    @Size(min = 6, max = 20, message = "(新密码)长度必须在6到20位之间")
    private String password;

    @Size(min = 6, max = 20, message = "(确认密码)长度必须在6到20位之间")
    private String confirmPassword;
}