package com.instorage.myproject.domain;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Repository
public class Validate {
    public String handleResponseId(RedirectAttributes rda, String resId){
        if(resId.equals("idNull")){
            rda.addFlashAttribute("error","아이디 값을 입력해주세요.");
            return "redirect:/register";
        }
        if(resId.equals("idSpace")){
            rda.addFlashAttribute("error","아이디 값은 공백을 포함할수 없습니다.");
            return "redirect:/register";
        }
        if(resId.equals("idLength")){
            rda.addFlashAttribute("error","아이디 값은 5 이상 19 이하여야 합니다.");
            return "redirect:/register";
        }
        rda.addFlashAttribute("error","서버 에러 발생.다시 시도해 주세요.");
        return "redirect:/register";
    }
    public String handleResponsePwd(RedirectAttributes rda,String resPwd){
        if(resPwd.equals("pwdNull")){
            rda.addFlashAttribute("error","비밀번호 값을 입력해주세요");
            return "redirect:/register";
        }
        if(resPwd.equals("pwdSpace")){
            rda.addFlashAttribute("error","비밀번호 값은 공백을 포함할수 없습니다.");
            return "redirect:/register";
        }
        if(resPwd.equals("pwdLength")){
            rda.addFlashAttribute("error","비밀번호 값은 8 이상 50 이하입니다.");
            return "redirect:/register";
        }
        rda.addFlashAttribute("error","서버 에러 발생.다시 시도해 주세요.");
        return "redirect:/register";
    }
    public String handleResponseNick(RedirectAttributes rda,String resNick){
        if(resNick.equals("nicknameNull")){
            rda.addFlashAttribute("error","닉네임 값을 입력해주세요.");
            return "redirect:/register";
        }
        if(resNick.equals("nicknameLength")){
            rda.addFlashAttribute("error","닉네임 값은 2 이상 20 이하입니다.");
            return "redirect:/register";
        }
        if(resNick.equals("nicknameSpace")){
            rda.addFlashAttribute("error","닉네임 값은 공백을 포함할수 없습니다.");
            return "redirect:/register";
        }
        rda.addFlashAttribute("error","서버 에러 발생.다시 시도해 주세요.");
        return "redirect:/register";
    }
    public String idCheck(String id){
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
    public String pwdCheck(String pwd){
        if(pwd == null || "".equals(pwd)){
            return "pwdNull";
        }
        if(pwd.length() <= 7 || pwd.length() >= 51){
            return "pwdLength";
        }
        if(pwd.contains(" ")){
            return "pwdSpace";
        }
        return "success";
    }
    public String nickCheck(String nickname){
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
    public String titleCheck(String title){
        if(title == null || "".equals(title)){
            return "titleNull";
        }
        if(title.length() > 100) {
            return "titleLength";
        }
        return "success";
    }
    public String contentCheck(String content){
        if(content == null || "".equals(content)) {
            return "contentNull";
        }
        return "success";
    }
    public String handleResponseTitle(String resTitle,RedirectAttributes rda,String type,Integer page,Integer pageSize){
        if(resTitle.equals("titleNull")){
            rda.addFlashAttribute("error","제목을 입력해주세요!");
            String uri = "redirect:/board/write?type="+type+"&page="+page+"&pageSize="+pageSize;
            return uri;
        }
        if(resTitle.equals("titleLength")){
            rda.addFlashAttribute("error","제목은 100글자를 넘을수 없습니다.");
            String uri = "redirect:/board/write?type="+type+"&page="+page+"&pageSize="+pageSize;
            return uri;
        }
        rda.addFlashAttribute("error","서버 에러 발생,다시 시도해주세요.");
        String uri = "redirect:/board/write?type="+type+"&page="+page+"&pageSize="+pageSize;
        return uri;
    }
    public String handleResponseContent(String resContent,RedirectAttributes rda,String type,Integer page,Integer pageSize){
        if(resContent.equals("contentNull")){
            rda.addFlashAttribute("error","제목을 입력해주세요!");
            String uri = "redirect:/board/write?type="+type+"&page="+page+"&pageSize="+pageSize;
            return uri;
        }
        rda.addFlashAttribute("error","서버 에러 발생.다시 시도해주세요.");
        String uri = "redirect:/board/write?type="+type+"&page="+page+"&pageSize="+pageSize;
        return uri;
    }
}
