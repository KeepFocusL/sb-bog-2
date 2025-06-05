package com.example.sbblog2.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("backend/user")
public class BackendUserController {

    @GetMapping("")
    public String list(){
        return "backend/user/list";
    }
}
