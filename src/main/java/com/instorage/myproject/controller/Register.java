package com.instorage.myproject.controller;

import com.instorage.myproject.domain.UserDto;
import com.instorage.myproject.domain.UserValidator;
import com.instorage.myproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLEncoder;

@Controller
public class Register {
    @Autowired
    UserService userService;
    @InitBinder
    public void toDate(WebDataBinder binder){
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(LocalDate.class,new CustomDateEditor(df,false));

        binder.setValidator(new UserValidator());
    }
    @GetMapping(value="/register")
    public String registerGet(){
        return "register";
    }
    @PostMapping(value="/register")
    public String registerPost(@Valid UserDto userDto, BindingResult result, Model m) throws Exception{
        if(result.hasErrors()){
            System.out.println(result);
            FieldError error =result.getFieldError();
            System.out.println(error.getField());
            return "register";
        }
        int key = userService.registerUser(userDto);
        if(key == 2){
            m.addAttribute("error","이미 존재하는 id입니다..");
            return "redirect:/register"; 
        }
        if(key == 3){
            m.addAttribute("error","이미 존재하는 nickname입니다.");
            return "redirect:/register";
        }
        if(key == 4){
            m.addAttribute("error","유효하지 않은 입력값입니다.다시 입력해주세요.");
            return "redirect:/register";
        }
        if(key == 5){
            m.addAttribute("error","sql 구문 오류");
            return "redirect:/register";
        }
        if(key == 6){
            m.addAttribute("error","jdbc 연결 오류");
            return "redirect:/register";
        }
        return "redirect:/";
            

    }
}