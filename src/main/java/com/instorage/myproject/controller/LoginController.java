package com.instorage.myproject.controller;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.instorage.myproject.domain.Validate;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/login")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    Validate validate;

    @GetMapping(value="/logout")
    public String logoutGet(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
    @PostMapping(value="/login")
    public String loginPost(String id, String pwd, boolean rememberId, Model m, RedirectAttributes rda, HttpServletResponse res, HttpServletRequest req){
        String resId = validate.idCheck(id);
        String resPwd = validate.pwdCheck(pwd);
        if(!resId.equals("success")){
            String redirectPath = validate.handleResponseId(rda,resId);
            return redirectPath;
        }
        if(!resPwd.equals("success")){
            String redirectPath = validate.handleResponsePwd(rda,resPwd);
            return redirectPath;
        }
        try {
            String loginCheck = userService.loginCheck(id,pwd);
            if(loginCheck.equals("NonexistentID")){
                rda.addFlashAttribute("error","존재하지 않는 아이디 입니다.다시 확인해주세요.");
                return "redirect:/";
            }
            if(loginCheck.equals("MismatchedPassword")){
                rda.addFlashAttribute("error","비밀번호가 일치하지 않습니다.다시 확인해주세요.");
                return "redirect:/";
            }
            // 아이디 기억하기 기능을 사용할시 쿠키를 한달동안 저장시킨다.
            // 체크하지 않았을 시 존재하는 쿠키를 제거한다.
            Cookie cookie;
            if(rememberId){
                cookie = new Cookie("id", id);
                cookie.setPath("/");
                cookie.setMaxAge(60*60*24*30);
            }else{
                cookie = new Cookie("id", id);
                cookie.setPath("/");
                cookie.setMaxAge(0);
            }
            res.addCookie(cookie);
            HttpSession session = req.getSession();
            session.setAttribute("id",id);
            // 세션 수명을 6시간으로 설정
            session.setMaxInactiveInterval(60*60*6);
            String nickname=userService.readUserById(id).getNickname();
            m.addAttribute("nickname",nickname);
            return "home";
        } catch (Exception e) {
            e.printStackTrace();
            rda.addFlashAttribute("error","에러가 발생했습니다.다시 시도해주세요");
            return "redirect:/";
        }
    }
}
