package com.hoyoon.board.controller;

import com.hoyoon.board.domain.User;
import com.hoyoon.board.domain.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Controller
public class Register {
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
    public String registerPost(@Valid User user, BindingResult result, Model m){
        if(result.hasErrors()){
            System.out.println(result);
            FieldError error =result.getFieldError();
            System.out.println(error.getField());
            return "register";
        }

        return "registerInfo";
    }
}
