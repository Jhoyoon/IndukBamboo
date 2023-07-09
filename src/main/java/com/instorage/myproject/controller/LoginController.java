package com.instorage.myproject.controller;

import com.instorage.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @GetMapping(value="/login")
    public String loginGet(){
        return "login";
    }
    @PostMapping(value="/login")
    public String loginPost(String id, String pwd, Model m){
        int loginCheck = userService.loginCheck(id,pwd);
        if(loginCheck == 1){
            m.addAttribute("error","존재하지 않는 id입니다.다시 확인해주세요.");
            return "redirect:/login";
        }
        if(loginCheck==2){
            m.addAttribute("error","pwd가 id와 일치하지 않습니다.다시 확인해주세요.");
            return "redirect:/login";
        }
        return "redirect:/";
    }

}
