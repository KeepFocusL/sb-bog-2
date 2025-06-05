package com.example.sbblog2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

    @Email
    String email;

    @Size(min = 3,max = 20,message = "用户名长度需在3-20之间")
    String name;

    @Size(min = 6,max = 15)
    String password;
}
