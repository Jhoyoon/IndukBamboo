package com.instorage.myproject.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.instorage.myproject.domain.UserDto;

import com.instorage.myproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;
    @GetMapping(value="/register")
    public String registerGet(){
        return "register";
    }
    @PostMapping(value="/register")
    public String registerPost(UserDto userDto, RedirectAttributes rda){
        String msg=registerCheck(userDto);
        if(!msg.equals("success")){
            if(msg.equals("idNull")){
                rda.addFlashAttribute("error","아이디 값을 입력해주세요.");
                return "redirect:/register";
            }
            if(msg.equals("idSpace")){
                rda.addFlashAttribute("error","아이디 값은 공백을 포함할수 없습니다.");
                return "redirect:/register";
            }
            if(msg.equals("idLength")){
                rda.addFlashAttribute("error","아이디 값은 5 이상 19 이하여야 합니다.");
                return "redirect:/register";
            }
            if(msg.equals("pwdNull")){
                rda.addFlashAttribute("error","비밀번호 값을 입력해주세요");
                return "redirect:/register";
            }
            if(msg.equals("pwdSpace")){
                rda.addFlashAttribute("error","비밀번호 값은 공백을 포함할수 없습니다.");
                return "redirect:/register";
            }
            if(msg.equals("pwdLength")){
                rda.addFlashAttribute("error","비밀번호 값은 8 이상 50 이하입니다.");
                return "redirect:/register";
            }
            if(msg.equals("pwdSpace")){
                rda.addFlashAttribute("error","비밀번호 값은 공백을 포함할수 없습니다.");
                return "redirect:/register";
            }
            if(msg.equals("nicknameNull")){
                rda.addFlashAttribute("error","닉네임 값을 입력해주세요.");
                return "redirect:/register";
            }
            if(msg.equals("nicknameLength")){
                rda.addFlashAttribute("error","닉네임 값은 2 이상 20 이하입니다.");
                return "redirect:/register";
            }
            if(msg.equals("nicknameSpace")){
                rda.addFlashAttribute("error","닉네임 값은 공백을 포함할수 없습니다.");
                return "redirect:/register";
            }
        }

        try{
            BCrypt.Hasher hasher = BCrypt.withDefaults();
            String hashedPassword = hasher.hashToString(10, userDto.getPwd().toCharArray());
            System.out.println(hashedPassword.length());
            userDto.setPwd(hashedPassword);
            String answer = userService.registerUser(userDto);
            if(answer.equals("success")){
                rda.addFlashAttribute("error","회원가입 감사합니다!상단에서 로그인 해주세요!");
                return "redirect:/";
            }else if(answer.equals("duplicateId")){
                rda.addFlashAttribute("error","이미 존재하는 아이디입니다.다르게 입력해주세요");
                return "redirect:/register";
            }
            else if(answer.equals("duplicateNickname")){
                rda.addFlashAttribute("error","이미 존재하는 닉네임입니다.다르게 입력해주세요.");
                return "redirect:/register";
            }
            else if(answer.equals("fail")){
                throw new Exception("회원가입 실패");
            }else{
                throw new Exception("회원가입 실패");
            }
        }catch (Exception e){
            e.printStackTrace();
            rda.addFlashAttribute("error","에러가 발생했습니다.다시 시도해주세요");
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
    @ResponseBody
    @PostMapping(value="/checkId")
    public ResponseEntity<Map<String,String>> checkDuplicate(@RequestBody Map<String, String> user){
        String id = user.get("id");
        String idCheck=idCheck(id);
        if(idCheck.equals("idNull")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "아이디를 입력해 주세요.");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        if(idCheck.equals("idLength")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "아이디 값은 5 이상 19 이하여야 합니다.");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        if(idCheck.equals("idSpace")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "아이디 값은 공백을 포함할수 없습니다.");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        try{
            boolean check = userService.checkUserById(id);
            if(!check){
                Map<String, String> response = new HashMap<>();
                response.put("error", "OK");
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .body(response);
            }else{
                Map<String, String> response = new HashMap<>();
                response.put("error", "이미 존재하는 아이디 입니다.");
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .body(response);
            }

        }catch (Exception e){
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("error", "서버 에러 발생.다시 시도해 주세요.");
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
    }
    @ResponseBody
    @PostMapping(value="/checkNickname")
    public ResponseEntity<Map<String,String>> checkNickname(@RequestBody Map<String, String> user){
        String nickname = user.get("nickname");
        String nicknameCheck=nicknameCheck(nickname);
        if(nicknameCheck.equals("nickNull")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "별명을 입력해 주세요.");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        if(nicknameCheck.equals("nickLength")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "별명 값은 2 이상 20 이하여야 합니다.");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        if(nicknameCheck.equals("nickSpace")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "별명 값은 공백을 포함할수 없습니다.");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        try{
            boolean check = userService.checkUserByNickname(nickname);
            Map<String, String> response = new HashMap<>();
            if(!check){
                response.put("error", "OK");
            }else{
                response.put("error", "이미 존재하는 별명 입니다.");
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);

        }catch (Exception e){
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("error", "서버 에러 발생.다시 시도해 주세요.");
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
    }
    private String idCheck(String id){
        if(id == null || "".equals(id)){
            return "idNull";
        }
        if(id.contains(" ")){
            return "idSpace";
        }
        if(id.length() <= 4 || id.length() >= 20){
            return "idLength";
        }
        return "success";
    }
    private String nicknameCheck(String nickname){
        if(nickname == null || "".equals(nickname)){
            return "nickNull";
        }
        if(nickname.contains(" ")){
            return "nickSpace";
        }
        if(nickname.length() <= 1 || nickname.length() >= 21){
            return "nickLength";
        }
        return "success";
    }
}
