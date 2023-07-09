package com.instorage.myproject.controller;

import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.PageHandler;
import com.instorage.myproject.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping(value="/remove")
    public String boardRemove(Integer bno,Integer page,Integer pageSize,Model m){
        boardService.remove(bno,"writer");
        m.addAttribute("page",page);
        m.addAttribute("pageSize",pageSize);
        return "redirect:/board/list";
    }
    @GetMapping(value="/list")
    public String boardList(Integer page, Integer pageSize, HttpServletRequest request, Model m){
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
        return "boardList";
    }
    @GetMapping(value="/read")
    public String boardRead(Integer bno,Integer page,Integer pageSize,Model m){
        BoardDto boardDto = boardService.read(bno);
        int totalCnt = boardService.count();
        PageHandler ph = new PageHandler(totalCnt,page,pageSize);
        m.addAttribute("ph",ph);
        m.addAttribute("board",boardDto);
        return "boardRead";
    }
}
