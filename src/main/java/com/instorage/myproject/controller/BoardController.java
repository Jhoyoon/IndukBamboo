package com.instorage.myproject.controller;

import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.PageHandler;
import com.instorage.myproject.domain.SearchCondition;
import com.instorage.myproject.service.BoardService;
import com.instorage.myproject.service.UserService;
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
    @Autowired
    UserService userService;
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
        String id = (String)session.getAttribute("id");

        if(id==null || "".equals(id)){
            m.addAttribute("error","로그인을 해야 게시판에 접속할수 있습니다.");
            return "redirect:/";
        }
        String nav = navCheck(sc.getType());
        try{
            int totalSize=boardService.countSearchPage(sc);
            PageHandler ph = new PageHandler(totalSize,sc);
            List<BoardDto> list =boardService.selectSearchPage(sc);
            String nickName=userService.readUserById(id).getNickname();
            m.addAttribute("list",list);
            m.addAttribute("ph",ph);
            m.addAttribute("nav",nav);
            m.addAttribute("nickName",nickName);
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
    public String boardRemove(String type,Integer bno, Integer page, Integer pageSize, Model m, HttpSession session,RedirectAttributes rda){
        String id =(String)session.getAttribute("id");
        if(type == null || "".equals(type)){
            m.addAttribute("error","게시판에 접근할수 없습니다.");
            return "redirect:/";
        }
        String uri;
        try{
            boolean check = boardService.checkBoardByBno(bno);
            if(!check){
                uri = "redirect:/board/list?"+"page=" + page + "&pageSize=" + pageSize;
                rda.addFlashAttribute("msg","게시물이 존재하지 않습니다.");
                return uri;
            }

            int answer = boardService.removeBoardByBnoAndWriter(bno,id);
            if(answer==1){
                uri = "redirect:/board/list?type="+type+"&page=" + page + "&pageSize=" + pageSize ;
                rda.addFlashAttribute("msg","삭제성공");
            }else{
                uri = "redirect:/board/read?type="+type+"&bno=" + bno + "&page=" + page + "&pageSize=" + pageSize;
                rda.addFlashAttribute("msg","게시물을 삭제할수 없습니다.");
            }
        }catch(Exception e) {
            e.printStackTrace();
            uri = "redirect:/board/read?type="+type+"&bno=" + bno + "&page=" + page + "&pageSize=" + pageSize;
            rda.addFlashAttribute("msg","게시물을 삭제할수 없습니다.");
            return uri;
        }
        return uri;
    }
   // *****************************************************************************
    // 특정 게시물 읽어오기
    @GetMapping(value="/read")
    public String boardRead(String type,Integer bno,Integer page,Integer pageSize,HttpSession session,Model m){
        String nav = navCheck(type);
        String id = (String)session.getAttribute("id");
        try{
            boolean answer = boardService.checkBoardByBno(bno);
            if(!answer){
                m.addAttribute("page",page);
                m.addAttribute("pageSize",pageSize);
                m.addAttribute("error","게시물이 존재하지 않습니다.");
                m.addAttribute("nav",nav);
                return "redirect:/board/list";
            }
            BoardDto boardDto = boardService.readBoardByBno(bno);
            boardService.increaseViewCntByBno(bno);
            String nickName=userService.readUserById(id).getNickname();
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
            m.addAttribute("board",boardDto);
            m.addAttribute("nav",nav);
            m.addAttribute("nickName",nickName);
            return "boardRead";
        }catch(Exception e){
            e.printStackTrace();
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
            m.addAttribute("error","게시물 방문시 에러가 발생했습니다.다시 시도해주세요.");
            m.addAttribute("nav",nav);
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
    public String boardEditGet(String type,Integer bno,String page,String pageSize,Model m){
        String nav=navCheck(type);
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
            m.addAttribute("nav",nav);
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
    public String boardEditPost(BoardDto boardDto,Integer pageSize,Integer page,String type,HttpSession session,Model m){
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
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
            m.addAttribute("type",type);
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
    private String navCheck(String type) {
         if(type.equals("silver" ) || type.equals("english") || type.equals("secretary") || type.equals("tour") || type.equals("china") || type.equals("japan") || type.equals("money") ||
             type.equals("watching") || type.equals("multi") || type.equals("webtoon") || type.equals("vr")){
                return "silver";
         }else if(type.equals("auditorium")){
                return "auditorium";
         }else if(type.equals("virtue") || type.equals("architecture") || type.equals("wood") || type.equals("safety") || type.equals("inner")){
                return "virtue";
         }else if(type.equals("person") || type.equals("industry") || type.equals("software") || type.equals("jewelry") || type.equals("ceramic") || type.equals("human")){
                return "person";
         }else if(type.equals("kinggod") || type.equals("kinggodgod") || type.equals("car")){
                return "kinggod";
         }else if(type.equals("library")){
             return "library";
         }else if(type.equals("engineering") || type.equals("computer") || type.equals("mecha") || type.equals("broadcast") || type.equals("information") || type.equals("air")){
             return "engineering";
         }else if(type.equals("welfare")){
             return "welfare";
         }else if(type.equals("formative") || type.equals("digital") || type.equals("influencer") || type.equals("city") || type.equals("beauty")){
             return "formative";
         }else if(type.equals("playground")){
             return "playground";
         }else if(type.equals("yenji") || type.equals("gym") || type.equals("foodcourt") || type.equals("stationery") || type.equals("printer")){
             return "yenji";
         }
         return "error";
    }
}
