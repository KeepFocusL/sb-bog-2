package com.example.sbblog2.controller;

import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("未登录的情况下访问 /user/dashboard 会被自动跳转到 /login")
    void userDashboardWithoutLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/dashboard"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "jpaUserDetailsService" , value = "user@example.com")
    void userDashboardWithLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/dashboard"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("个人资料")))
                .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("user@example.com")))
                .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("用户名")))
        ;
    }

    @Test
    void userRegisterWithExistingEmail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "admin@example.com")
                        .param("name", "admin")
                        .param("password", "password")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("user",  "email", "exist"))
        ;

    }

    @Test
    void register() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", System.currentTimeMillis() + "@example.com")
                        .param("name", System.currentTimeMillis() + "test")
                        .param("password", "password")
                )
                // 因为用户注册时会帮忙直接登录，所以验证的不是 isOk 而是 is3xxRedirection
                /*
                    // 帮该用户自动登录
                    httpServletRequest.login(userDTO.getEmail(), userDTO.getPassword());
                */
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
        ;

    }
}
