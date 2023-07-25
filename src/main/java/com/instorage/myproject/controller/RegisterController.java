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
    @GetMapping(value="/register")
    public String registerGet(){
        return "register";
    }
    @PostMapping(value="/register")
    public String registerPost(UserDto userDto, Model m){
        String msg=registerCheck(userDto);
        if(!msg.equals("success")){
            if(msg.equals("idNull")){
                m.addAttribute("error","아이디 값을 입력해주세요.");
                return "redirect:/register";
            }
            if(msg.equals("idSpace")){
                m.addAttribute("error","아이디 값은 공백을 포함할수 없습니다.");
                return "redirect:/register";
            }
            if(msg.equals("idLength")){
                m.addAttribute("error","아이디 값은 5 이상 19 이하여야 합니다.");
                return "redirect:/register";
            }
            if(msg.equals("pwdNull")){
                m.addAttribute("error","비밀번호 값을 입력해주세요");
                return "redirect:/register";
            }
            if(msg.equals("pwdSpace")){
                m.addAttribute("error","비밀번호 값은 공백을 포함할수 없습니다.");
                return "redirect:/register";
            }
            if(msg.equals("pwdLength")){
                m.addAttribute("error","비밀번호 값은 8 이상 50 이하입니다.");
                return "redirect:/register";
            }
            if(msg.equals("pwdSpace")){
                m.addAttribute("error","비밀번호 값은 공백을 포함할수 없습니다.");
                return "redirect:/register";
            }
            if(msg.equals("nicknameNull")){
                m.addAttribute("error","닉네임 값을 입력해주세요.");
                return "redirect:/register";
            }
            if(msg.equals("nicknameLength")){
                m.addAttribute("error","닉네임 값은 2 이상 20 이하입니다.");
                return "redirect:/register";
            }
            if(msg.equals("nicknameSpace")){
                m.addAttribute("error","닉네임 값은 공백을 포함할수 없습니다.");
                return "redirect:/register";
            }
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

    private String registerCheck(UserDto userDto) {
        String id = userDto.getId();
        String pwd = userDto.getPwd();
        String nickname = userDto.getNickname();

        if(id == null || "".equals(id)){
            return "idNull";
        }
        if(id.length() <= 4 || id.length() >= 20){
            return "idLength";
        }
        if(id.contains(" ")){
            return "idSpace";
        }
        if(pwd == null || "".equals(pwd)){
            return "pwdNull";
        }
        if(pwd.length() <= 7 || pwd.length() >= 51){
            return "pwdLength";
        }
        if(pwd.contains(" ")){
            return "pwdSpace";
        }
        // nickname 유효성 검사
        if(nickname == null || "".equals(nickname)){
            return "nicknameNull";
        }
        if(nickname.contains(" ")){
            return "nicknameSpace";
        }
        if(nickname.length() <= 1 || nickname.length() >= 21){
            return "nicknameLength";
        }


        return "success";
    }
}
