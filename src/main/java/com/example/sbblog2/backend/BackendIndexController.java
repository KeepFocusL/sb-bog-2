package com.example.sbblog2.backend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("backend")
public class BackendIndexController {

    @GetMapping()
    public String index(Model model){
        model.addAttribute("requestURI", "/backend");
        return "backend/blank";
    }
}
