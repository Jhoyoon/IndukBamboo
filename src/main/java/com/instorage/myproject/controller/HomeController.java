package com.instorage.myproject.controller;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@GetMapping(value="/")
	public String main(Model m) throws Exception{
//		String msg=null;
//		try{
//			msg = URLEncoder.encode("이 요청은 redirect로 왔습니다","utf-8");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return "redirect:/home2?msg="+msg;
//		String msg = URLEncoder.encode("이 요청은 redirect로 왔습니다.","utf-8");
//		String msg2 = "이 요청은 forward를 통해서 왔습니다.";
//		m.addAttribute("msg",msg2);
//		return "forward:/home2";
		return "home";
	}
//	@GetMapping(value="/home2")
//	public String main2(){
//		return "home2";
//	}
	
}
