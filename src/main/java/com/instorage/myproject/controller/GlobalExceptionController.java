package com.instorage.myproject.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(value = Exception.class)
    public String handleError429(Model m) {
            System.out.println("if문이 작동했습니다.error429를 던집니다.");
            return "errorGlobal";
    }
}
