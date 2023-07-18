package com.instorage.myproject.controller;

import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.PageHandler;
import com.instorage.myproject.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping(value="/remove")
    public String boardRemove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session,RedirectAttributes rda){
        String id =(String)session.getAttribute("id");
        int result = boardService.remove(bno,id);
        System.out.println(result);
        String uri;
        if(result == 1){
            uri = "redirect:/board/list?page=" + page + "&pageSize=" + pageSize ;
            rda.addFlashAttribute("msg","삭제성공");
        }else{
            uri = "redirect:/board/read?bno=" + bno + "&page=" + page + "&pageSize=" + pageSize;
            rda.addFlashAttribute("msg","삭제실패");
        }
        return uri;
    }
    @GetMapping(value="/list")
    public String boardList(Integer page, Integer pageSize, HttpServletRequest request, Model m){
        // req에서 sessionid로 서버에서 세션을 찾는다.
        HttpSession session = request.getSession();
        //그 세션에서 id를 찾잖아.
//        System.out.println("session id="+session.getAttribute("id"));
        if(session.getAttribute("id")==null){
            m.addAttribute("uri","/board/list");
            return "redirect:/login/login";
        }
        if(page == null) page = 1;
        if(pageSize == null) pageSize = 10;
            int totalSize=boardService.count();
            PageHandler ph = new PageHandler(totalSize,page,pageSize);
            Map map = new HashMap();
            map.put("offset",(page-1)*pageSize);
            map.put("pageSize",pageSize);
            List<BoardDto> list =boardService.getPage(map);
            m.addAttribute("list",list);
            m.addAttribute("ph",ph);
        return "boardList";
    }
    @GetMapping(value="/read")
    public String boardRead(Integer bno,Integer page,Integer pageSize,Model m){
        BoardDto boardDto = boardService.read(bno);
        int totalCnt = boardService.count();
        PageHandler ph = new PageHandler(totalCnt,page,pageSize);
        System.out.println(boardService.increaseView(bno));
        m.addAttribute("ph",ph);
        m.addAttribute("board",boardDto);
        return "boardRead";
    }
    @GetMapping(value="/write")
    public String boardWriteGet(Integer pageSize,Model m){
        if(pageSize == null) pageSize = 10;
        m.addAttribute("pageSize",pageSize);
        return "boardWrite";
    }
    @PostMapping(value="/write")
    public String boardWritePost(BoardDto boardDto,HttpSession session,Model m){
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);
        boardService.write(boardDto);
        return "redirect:/board/list";
    }
    @GetMapping(value="/edit")
    public String boardEditGet(Integer bno,String page,String pageSize,Model m){
        BoardDto boardDto = boardService.read(bno);
        m.addAttribute("mode","edit");
        m.addAttribute("boardDto",boardDto);
        return "boardEdit";
    }
    @PostMapping(value="/edit")
    public String boardEditPost(BoardDto boardDto,Integer pageSize,HttpSession session,Model m){
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter("writer");
        boardService.update(boardDto);
        m.addAttribute("bno",boardDto.getBno());
        m.addAttribute("page",1);
        m.addAttribute("pageSize",pageSize);
        return "redirect:/board/read";
    }
}
