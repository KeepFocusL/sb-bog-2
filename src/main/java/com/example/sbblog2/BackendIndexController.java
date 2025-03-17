package com.example.sbblog2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("backend")
public class BackendIndexController {

    @GetMapping()
    public String index(){
        return "backend/blank";
    }
}
