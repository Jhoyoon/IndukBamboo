package com.instorage.myproject.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.instorage.myproject.domain.UserDto;

import com.instorage.myproject.domain.Validate;
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
    @Autowired
    Validate validate;
    @GetMapping(value="/register")
    public String registerGet(){
        return "register";
    }
    @PostMapping(value="/register")
    public String registerPost(UserDto userDto, RedirectAttributes rda){
        // 회원가입 시도 전 유효성 검사
        String id = userDto.getId();
        String nickname = userDto.getNickname();
        String resId = validate.idCheck(id);
        String resNick = validate.nickCheck(nickname);

        if(!resId.equals("success")){
            String redirectPath = validate.handleResponseId(rda,resId);
            return redirectPath;
        }
        if(!resNick.equals("success")){
            String redirectPath = validate.handleResponseNick(rda,resNick);
            return redirectPath;
        }

        try{
            // 비밀번호를 암호화 한다.
            char[] originalPassword = userDto.getPwd().toCharArray();
            String hashedPassword = hashingPassword(originalPassword);
            userDto.setPwd(hashedPassword);

            // 회원가입을 시도한다.적절한 메세지를 넣어준다.
            String resInTry = userService.registerUser(userDto);
            String redirectPathInTry = handleResponseInTry(rda,resInTry);
            return redirectPathInTry;
        }catch (Exception e){
            e.printStackTrace();
            rda.addFlashAttribute("error","서버 에러 발생.다시 시도해주세요");
            return "redirect:/register";
        }
    }

    // api 요청에 대한 응답들
    @ResponseBody
    @PostMapping(value="/checkId")
    public ResponseEntity<Map<String,String>> checkDuplicate(@RequestBody Map<String, String> user){
        // id 유효성 체크
        String id = user.get("id");
        String res= validate.idCheck(id);
        if(!res.equals("success")){
            ResponseEntity resEn =  apiHandleId(res);
            return resEn;
        }
        // 아이디 중복 체크
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
        String res = validate.nickCheck(nickname);
        if(!res.equals("success")){
            ResponseEntity resEn = apiHandleNickname(res);
            return resEn;
        }
        // 닉네임 중복 체크
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

    // 유효성 검사

    // 비밀번호를 암호화 해준다.
    private String hashingPassword(char[] originalPassword) {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        String hashedPassword = hasher.hashToString(10, originalPassword);
        return hashedPassword;
    }
    // resIntry 값에 따른 적절한 에러 메세지를 넣어준다.
    private String handleResponseInTry(RedirectAttributes rda,String resInTry){
        if(resInTry.equals("success")){
            rda.addFlashAttribute("error","회원가입 감사합니다! 상단에서 로그인 해주세요!");
            return "redirect:/";
        }
        if(resInTry.equals("duplicateId")){
            rda.addFlashAttribute("error","이미 존재하는 아이디입니다.다르게 입력해주세요");
            return "redirect:/register";
        }

        if(resInTry.equals("duplicateNickname")){
            rda.addFlashAttribute("error","이미 존재하는 닉네임입니다.다르게 입력해주세요.");
            return "redirect:/register";
        }
        if(resInTry.equals("fail")){
            rda.addFlashAttribute("error","회원가입에 실패했습니다.다시 시도해주세요.");
            return "redirect:/register";
        }
        rda.addFlashAttribute("error","회원가입에 실패했습니다.다시 시도해주세요.");
        return "redirect:/register";
    }
    private ResponseEntity apiHandleId(String res){
        if(res.equals("idNull")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "아이디를 입력해 주세요.");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        if(res.equals("idLength")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "아이디 값은 5 이상 20 이하여야 합니다.");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        if(res.equals("idSpace")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "아이디 값은 공백을 포함할수 없습니다.");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        Map<String, String> response = new HashMap<>();
        response.put("error", "서버 에러 발생.다시 시도해주세요.");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .body(response);
    }
    private ResponseEntity apiHandleNickname(String res){
        if(res.equals("nickNull")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "별명을 입력해 주세요.");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        if(res.equals("nickLength")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "별명 값은 2 이상 20 이하여야 합니다.");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        if(res.equals("nickSpace")){
            Map<String, String> response = new HashMap<>();
            response.put("error", "별명 값은 공백을 포함할수 없습니다.");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        Map<String, String> response = new HashMap<>();
        response.put("error", "서버 에러 발생.다시 시도해주세요.");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .body(response);
    }


}
