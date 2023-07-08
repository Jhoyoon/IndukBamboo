package com.instorage.myproject.controller;

import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.PageHandler;
import com.instorage.myproject.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {
    @Autowired
    BoardService boardService;
    @GetMapping(value="/board/list")
    public String boardGet(Integer page, Integer pageSize, HttpServletRequest request, Model m){
        if(page == null) page = 1;
        if(pageSize == null) pageSize = 10;
        try{
            int totalSize=boardService.count();
            PageHandler ph = new PageHandler(totalSize,page,pageSize);
            Map map = new HashMap();
            map.put("offset",(page-1)*pageSize);
            map.put("pageSize",pageSize);
            List<BoardDto> list =boardService.getPage(map);
            m.addAttribute("list",list);
            m.addAttribute("ph",ph);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "board";
    }
}
