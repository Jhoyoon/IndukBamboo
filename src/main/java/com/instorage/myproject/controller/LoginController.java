package com.instorage.myproject.controller;

import com.instorage.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.Date;

@Controller
@RequestMapping(value="/login")
public class LoginController {
    @Autowired
    UserService userService;
    @GetMapping(value="/login")
    public String loginGet(HttpServletRequest req){
        String referrer = req.getHeader("Referer");
        return "login";
    }
    @GetMapping(value="/logout")
    public String logoutGet(HttpSession session){
        session.invalidate();
        System.out.println("logout에 성공했습니다.");
        //
        return "redirect:/";
    }
    @PostMapping(value="/login")
    public String loginPost(String id, String pwd,boolean rememberId,String uri, Model m, HttpServletResponse res, HttpServletRequest req){
        int loginCheck = userService.loginCheck(id,pwd);
        if(loginCheck == 1){
            m.addAttribute("error","존재하지 않는 id입니다.다시 확인해주세요.");
            return "redirect:/login/login";
        }
        if(loginCheck==2){
            m.addAttribute("error","pwd가 id와 일치하지 않습니다.다시 확인해주세요.");
            return "redirect:/login/login";
        }
        // rememberId를 체크해야만 쿠키를 준다.그렇다면 로그인 상태 유지는 어떻게 하지?
        // 로그인 상태 유지는 sessionid로 하는거다!

        Cookie cookie;
        if(rememberId){
            cookie = new Cookie("id", id);
            cookie.setPath("/");
            cookie.setMaxAge(60*60);
        }else{
            cookie = new Cookie("id", id);
            cookie.setPath("/");
            cookie.setMaxAge(0);
        }
        res.addCookie(cookie);
        HttpSession session = req.getSession();
        session.setAttribute("id",id);
        // session이 만들어진 시간을 이런식으로 볼수있다.
//        long creationTime = session.getCreationTime();
//        Date creationDate = new Date(creationTime);
//        System.out.println("Session creation time: " + creationDate);
//        // 어째서 req로부터 session을 얻어오는가?요청 헤더에 jsessionid가 들어있기 때문.
//        // 아 쿠키는 그냥 아이디 저장용이지 로그인 할 때 사용하는게 전혀 아니구나
//        System.out.println("sessionID = "+session.getId());
//        System.out.println("seesion은 새거인가? "+session.isNew());
        // 로그인 검증 성공

        // 없을시 홈으로 이동
        if(uri == null) uri="/";
        return "redirect:"+uri;
    }

}
