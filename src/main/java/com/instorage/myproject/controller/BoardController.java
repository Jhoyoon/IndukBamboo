package com.instorage.myproject.controller;

import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.PageHandler;
import com.instorage.myproject.domain.SearchCondition;
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
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/board")
public class BoardController {
    @Autowired
    BoardService boardService;
    // ***************************************************************************
    // 모든 게시물 보여주기
    @GetMapping(value="/list")
    public String boardList(SearchCondition sc, HttpServletRequest request, Model m){
        if(sc.getPage() == null) sc.setPage(1);
        if(sc.getPageSize() == null) sc.setPageSize(10);
        if(sc.getType() == null || "".equals(sc.getType())){
            m.addAttribute("error","게시판에 접근할수 없습니다.");
            return "redirect:/";
        }
        // req에서 sessionid로 서버에서 세션을 찾는다.
        HttpSession session = request.getSession();

        if(session.getAttribute("id")==null){
            return "redirect:/login/login";
        }
        try{
            int totalSize=boardService.countSearchPage(sc);
            PageHandler ph = new PageHandler(totalSize,sc);
            System.out.println(ph.getSc().getPageSize());
            List<BoardDto> list =boardService.selectSearchPage(sc);
            m.addAttribute("list",list);
            m.addAttribute("ph",ph);
            return "boardList";
        }catch(Exception e){
            e.printStackTrace();
            m.addAttribute("error","페이지 로드시 에러가 발생했습니다.다시 시도해주세요.");
            return "redirect:/";
        }
    }
    // ************************************************************************
    // 게시물 제거
    @GetMapping(value="/remove")
    public String boardRemove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session,RedirectAttributes rda){
        String id =(String)session.getAttribute("id");
        String uri;
        try{
            boolean check = boardService.checkBoardByBno(bno);
            System.out.println(check);
            if(!check){
                uri = "redirect:/board/list?"+"page=" + page + "&pageSize=" + pageSize;
                rda.addFlashAttribute("msg","게시물이 존재하지 않습니다.");
                return uri;
            }

            int answer = boardService.removeBoardByBnoAndWriter(bno,id);
            if(answer==1){
                uri = "redirect:/board/list?page=" + page + "&pageSize=" + pageSize ;
                rda.addFlashAttribute("msg","삭제성공");
            }else{
                uri = "redirect:/board/read?bno=" + bno + "&page=" + page + "&pageSize=" + pageSize;
                rda.addFlashAttribute("msg","게시물을 삭제할수 없습니다.");
            }
        }catch(Exception e) {
            e.printStackTrace();
            uri = "redirect:/board/read?bno=" + bno + "&page=" + page + "&pageSize=" + pageSize;
            rda.addFlashAttribute("msg","게시물을 삭제할수 없습니다.");
            return uri;
        }
        return uri;
    }
   // *****************************************************************************
    // 특정 게시물 읽어오기
    @GetMapping(value="/read")
    public String boardRead(Integer bno,Integer page,Integer pageSize,Model m){
        try{
            boolean answer = boardService.checkBoardByBno(bno);
            if(!answer){
                m.addAttribute("page",page);
                m.addAttribute("pageSize",pageSize);
                m.addAttribute("error","게시물이 존재하지 않습니다.");
                return "redirect:/board/list";
            }
            BoardDto boardDto = boardService.readBoardByBno(bno);
            boardService.increaseViewCntByBno(bno);
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
            m.addAttribute("board",boardDto);
            return "boardRead";
        }catch(Exception e){
            e.printStackTrace();
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
            m.addAttribute("error","게시물 방문시 에러가 발생했습니다.다시 시도해주세요.");
            return "redirect:/board/list";
        }
    }
    // ********************************************************************
    // 게시물 작성하기
    @GetMapping(value="/write")
    public String boardWriteGet(String type,Integer page,Integer pageSize,Model m){
        if(pageSize == null) pageSize = 10;
        if(type == null){
            m.addAttribute("error","페이지에 접근할수 없습니다.");
            return "redirect:/";
        }
        return "boardWrite";
    }
    @PostMapping(value="/write")
    public String boardWritePost(String type,String title,String content,Integer page,Integer pageSize,HttpSession session,Model m){
        String writer = (String)session.getAttribute("id");
        BoardDto boardDto = new BoardDto(type,writer,title,content);
        System.out.println(type);
        if(type == null || "".equals(type)){
            m.addAttribute("error","게시물 등록에 실패했습니다.");
            return "redirect:/";
        }
        try{
            boardService.writeBoard(boardDto);
            m.addAttribute("type",type);
            m.addAttribute("pageSize",pageSize);
            return "redirect:/board/list";
        }catch(Exception e){
            e.printStackTrace();
            m.addAttribute("error","게시물 등록에 실패했습니다.다시 시도해주세요.");
            m.addAttribute("boardDto",boardDto);
            return "forward:/board/write?type="+type+"&page="+page+"&pageSize="+pageSize;
        }
    }
    // *************************************************************************
    // 게시물 수정하기
    @GetMapping(value="/edit")
    public String boardEditGet(Integer bno,String page,String pageSize,Model m){
        try{
            boolean answer = boardService.checkBoardByBno(bno);
            if(!answer){
                m.addAttribute("page",page);
                m.addAttribute("pageSize",pageSize);
                m.addAttribute("error","게시물이 존재하지 않습니다.");
                return "redirect:/board/list";
            }
            BoardDto boardDto = boardService.readBoardByBno(bno);
            m.addAttribute("mode","edit");
            m.addAttribute("boardDto",boardDto);
            m.addAttribute("pageSize",pageSize);
            //throw new Exception("test");
            return "boardWrite";
        }catch(Exception e){
            e.printStackTrace();
            m.addAttribute("bno",bno);
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
            m.addAttribute("error","페이지 방문에 실패했습니다.다시 시도해주세요.");
            return "redirect:/board/read";
        }
    }
    @PostMapping(value="/edit")
    public String boardEditPost(BoardDto boardDto,Integer pageSize,HttpSession session,Model m){
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);
        try{
            boolean answer = boardService.checkBoardByBno(boardDto.getBno());
            if(!answer){
                m.addAttribute("pageSize",pageSize);
                m.addAttribute("error","게시물이 존재하지 않습니다.");
                return "redirect:/board/list";
            }
            boardService.updateBoard(boardDto);
            m.addAttribute("bno",boardDto.getBno());
            m.addAttribute("page",1);
            m.addAttribute("pageSize",pageSize);
            return "redirect:/board/read";
        }catch (Exception e){
            e.printStackTrace();
            m.addAttribute("mode","edit");
            m.addAttribute("boardDto",boardDto);
            m.addAttribute("page",1);
            m.addAttribute("pageSize",pageSize);
            return "boardWrite";
        }
    }
}
