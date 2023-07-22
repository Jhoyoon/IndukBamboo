package com.instorage.myproject.controller;

import com.instorage.myproject.domain.UserDto;
import com.instorage.myproject.validator.UserValidator;
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

@Controller
public class RegisterController {
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
    public String registerPost(@Valid UserDto userDto, BindingResult result, Model m){
        // validator에서 데이터 유효성 검증
        if(result.hasErrors()){
            System.out.println(result);
            FieldError error =result.getFieldError();
            System.out.println(error.getField());
            return "register";
        }
        try{
            String answer = userService.registerUser(userDto);
            if(answer.equals("success")){
                return "redirect:/login/login";
            }else if(answer.equals("duplicateId")){
                m.addAttribute("error","이미 존재하는 아이디입니다.다르게 입력해주세요");
                return "redirect:/register";
            }
            else if(answer.equals("duplicateNickname")){
                m.addAttribute("error","이미 존재하는 닉네임입니다.다르게 입력해주세요.");
                return "redirect:/register";
            }
            else if(answer.equals("fail")){
                throw new Exception("회원가입 실패");
            }else{
                throw new Exception("회원가입 실패");
            }

        }catch (Exception e){
            e.printStackTrace();
            m.addAttribute("error","에러가 발생했습니다.다시 시도해주세요");
            return "redirect:/register";
        }
    }
}
