package com.instorage.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping(value="/login")
    public String loginGet(String id,String pwd){
        return "login";
    }
}
