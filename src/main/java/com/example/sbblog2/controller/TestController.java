package com.example.sbblog2.controller;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    JavaMailSender sender;

    @GetMapping("send-mail")
    String send() throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(new InternetAddress("1281438594@qq.com", "客服"));
        helper.setSubject("忘记密码 Subject");
        helper.setTo("1281438594@qq.com");
        helper.setText("邮件发生成功 - 邮件正文");

        sender.send(message);

        return "200";
    }

        @GetMapping("logger")
        String testLogger(){
            logger.debug("debug 级别的日志");
            logger.info("info 级别的日志");
            logger.warn("warn 级别的日志");
            logger.error("error 级别的日志");
            return "看日志文件";
        }
    }
