package com.example.sbblog2.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("backend/user")
public class BackendUserController {

    @GetMapping("")
    public String list(){
        // 找数据库要数据，由于不建议跨层所以找 Service 要对应的数据
        // 由于需要 Service 找 IoC 容器拿(从 IoC 容器中注入)
        // 拿到数据后返回给前端
        // 前端拿到数据后按照需要的方式展示数据
        return "backend/user/list";
    }
}
