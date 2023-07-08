package com.instorage.myproject.controller;

import com.instorage.myproject.domain.UserDto;
import com.instorage.myproject.domain.UserValidator;
import com.instorage.myproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        int rowCnt = 0;
        try{
        rowCnt=userService.registerUser(userDto);
        }catch (Exception e){
            e.printStackTrace();
            String msg = URLEncoder.encode("회원가입에 실패했습니다.다시 시도해주세요.","utf-8");
            return "redirect:/register?msg="+msg;
        }
        if(rowCnt == 1) return "registerInfo";
        else{
            String msg = URLEncoder.encode("회원가입에 실패했습니다.다시 시도해주세요.","utf-8");
            return "redirect:/register?msg="+msg;
        }
    }
}
