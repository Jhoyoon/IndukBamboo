package com.instorage.myproject.controller;

import com.instorage.myproject.domain.*;
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
import java.util.*;

@Controller
@RequestMapping(value="/board")
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    UserService userService;
    @Autowired
    Validate validate;
    // ***************************************************************************
    // 모든 게시물 보여주기
    @GetMapping(value="/list")
    public String boardList(SearchCondition sc, HttpSession session,RedirectAttributes rda,Model m){
        if(sc.getPage() == null) sc.setPage(1);
        if(sc.getPageSize() == null) sc.setPageSize(30);
        if(sc.getType() == null || "".equals(sc.getType())){
            rda.addFlashAttribute("error","게시판에 접근할수 없습니다.");
            return "redirect:/";
        }
        // req에서 sessionid로 서버에서 세션을 찾는다.
        String id = getSessionId(session);
        // 연지스퀘어의 경우 visiter 계정을 부여하고 접근을 허가한다.
        if((id==null||"".equals(id)) && (sc.getType().equals("yenji") || sc.getType().equals("gym") || sc.getType().equals("printer") || sc.getType().equals("foodcourt") || sc.getType().equals("stationery"))){
            id = "visiter";
        }
        if(id==null || "".equals(id)){
            rda.addFlashAttribute("error","로그인 해야 대나무숲에 접근할수 있습니다!");
            return "redirect:/";
        }
        String nav = navCheck(sc.getType());
        String title = titleCheck(sc.getType());

        try{
            // 헤더에 들어가는 닉네임
            String nickname = userService.readUserById(id).getNickname();

            // ***************** 페이지 list 로직
            int totalSize=boardService.countSearchPage(sc);
            PageHandler ph = new PageHandler(totalSize,sc);
            List<BoardDto> list =boardService.selectSearchPage(sc);
            if(list.size() == 0) m.addAttribute("none","*게시물이 없습니다*");
            // *****************************

            m.addAttribute("list",list);
            m.addAttribute("ph",ph);
            m.addAttribute("nav",nav);
            m.addAttribute("title",title);
            m.addAttribute("nickname",nickname);
            return "boardList";
        }catch(Exception e){
            e.printStackTrace();
            rda.addFlashAttribute("error","페이지 로드시 에러 발생.다시 시도해주세요.");
            return "redirect:/";
        }
    }


    // ************************************************************************
    // 게시물 제거
    @GetMapping(value="/remove")
    public String boardRemove(String type,Integer bno, Integer page, Integer pageSize,HttpSession session,RedirectAttributes rda){
        String id =getSessionId(session);
        if(id == null || "".equals(id)){
            rda.addFlashAttribute("error","로그인 해야 대나무숲에 접근할수 있습니다!");
            return "redirect:/";
        }
        if(type == null || "".equals(type)){
            rda.addFlashAttribute("error","게시판에 접근할수 없습니다.");
            return "redirect:/";
        }
        String uri;
        try{
            // ************************** 게시물이 존재하는지 확인한다.****************************
            boolean check = boardService.checkBoardByBno(bno);
            if(!check){
                uri = "redirect:/board/list?type="+type+"&page=" + page + "&pageSize=" + pageSize;
                rda.addFlashAttribute("error","게시물이 존재하지 않습니다.");
                return uri;
            }
            // *******************************************************************************

            // ************************* 삭제하는 사람이 작성자인지 확인한다*************************
            BoardDto boardDto = boardService.readBoardByBno(bno);
            String writer = boardDto.getWriter();
            if(!(writer.equals(id))){
                uri = "redirect:/board/list?type="+type+"&page=" + page + "&pageSize=" + pageSize;
                rda.addFlashAttribute("error","게시물은 작성자만 삭제할수 있습니다.");
                return uri;
            }
            // *******************************************************************************************
            // ********************************게시물 삭제 로직 *********************************************
            int answer = boardService.removeBoardByBnoAndWriter(bno,id);
            if(answer==1){
                uri = "redirect:/board/list?type="+type+"&page=" + page + "&pageSize=" + pageSize ;
                rda.addFlashAttribute("error","삭제성공");
            }else{
                uri = "redirect:/board/read?type="+type+"&bno=" + bno + "&page=" + page + "&pageSize=" + pageSize;
                rda.addFlashAttribute("error","게시물 삭제에 실패했습니다.");
            }
            // ***************************************************************************************************
        }catch(Exception e) {
            e.printStackTrace();
            uri = "redirect:/board/read?type="+type+"&bno=" + bno + "&page=" + page + "&pageSize=" + pageSize;
            rda.addFlashAttribute("error","게시물을 삭제할수 없습니다.");
            return uri;
        }
        return uri;
    }
   // *****************************************************************************
    // 특정 게시물 읽어오기
    @GetMapping(value="/read")
    public String boardRead(String type,Integer bno,Integer page,Integer pageSize,RedirectAttributes rda,Model m,HttpSession session){
        String id = getSessionId(session);
        // 연지스퀘어의 경우 visiter 계정을 부여하자
        if((id==null || "".equals(id)) && (type.equals("yenji") || type.equals("gym") || type.equals("printer") || type.equals("foodcourt") || type.equals("stationery")) ){
            id = "visiter";
        }
        if(id == null || "".equals(id)){
            rda.addFlashAttribute("error","로그인 해야 대나무숲에 접근할수 있습니다!");
            return "redirect:/";
        }
        if(type == null || "".equals(type)){
            rda.addFlashAttribute("error","페이지에 접근할수 없습니다.");
            return "redirect:/";
        }

        String nav = navCheck(type);
        String title = titleCheck(type);

        try{
            // *********************게시물이 존재하는지 확인한다.***************************************
            boolean answer = boardService.checkBoardByBno(bno);
            if(!answer){
                String uri = "redirect:/board/list?page="+page+"&pageSize="+pageSize+"&nav="+nav;
                rda.addFlashAttribute("error","게시물이 존재하지 않습니다.");
                return uri;
            }
            // *****************************************************************************************
            // 값을 넘기는데 사용하는 boardDto
            BoardDto boardDto = boardService.readBoardByBno(bno);
            // 헤더에 사용할 nickname
            String nickname = userService.readUserById(id).getNickname();
            // 게시물에 사용할 nickname
            UserDto userDto = userService.readUserById(boardDto.getWriter());
            String board_nickname = userDto.getNickname();
            m.addAttribute("board_nickname",board_nickname);
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
            m.addAttribute("board",boardDto);
            m.addAttribute("nav",nav);
            m.addAttribute("nickname",nickname);
            m.addAttribute("title",title);
            return "boardRead";
        }catch(Exception e){
            e.printStackTrace();
            String uri="redirect:/board/list?page="+page+"&pageSize="+pageSize+"&nav="+nav;
            rda.addFlashAttribute("error","게시물 방문시 에러가 발생했습니다.다시 시도해주세요.");
            return uri;
        }
    }
    // ********************************************************************
    // 게시물 작성하기
    @GetMapping(value="/write")
    public String boardWriteGet(String type,Integer page,Integer pageSize,RedirectAttributes rda,Model m,HttpSession session){
        String id = getSessionId(session);
        // 연지스퀘어는 로그인을 안 해도 접근할수 있다.연지스퀘어에서 글을 작성하려고 시도 할 경우 다른 메세지를 보여주자
        if((id == null || "".equals(id)) && (type.equals("yenji") || type.equals("gym") || type.equals("printer") || type.equals("foodcourt") || type.equals("stationery"))){
            rda.addFlashAttribute("error","글은 로그인을 해야 작성할수 있습니다.회원가입 고고");
            String uri="redirect:/board/list?type="+type+"&page="+page+"&pageSize="+pageSize;
            return uri;
        }

        if(id == null || "".equals(id)){
            rda.addFlashAttribute("error","로그인 해야 대나무숲에 접근할수 있습니다!");
            return "redirect:/";
        }


        if(type == null || "".equals(type)){
            rda.addFlashAttribute("error","페이지에 접근할수 없습니다.");
            return "redirect:/";
        }
        String nav =navCheck(type);
        String title = titleCheck(type);
        try{
            String nickname = userService.readUserById(id).getNickname();
            m.addAttribute("nickname",nickname);
            m.addAttribute("nav",nav);
            m.addAttribute("title",title);
        }catch (Exception e){
            rda.addFlashAttribute("error","페이지 접근에 실패했습니다.");
            String uri = "redirect:/type="+type+"&page="+page+"&pageSize="+pageSize;
            return uri;
        }
        return "boardWrite";
    }

    @PostMapping(value="/write")
    public String boardWritePost(String type,String title,String content,Integer page,Integer pageSize,HttpSession session,RedirectAttributes rda,Model m){
        String writer = getSessionId(session);
        if(type == null || "".equals(type)){
            rda.addFlashAttribute("error","게시물 등록에 실패했습니다.");
            return "redirect:/";
        }

        // 게시글 입력값 유효성 검증
        String resTitle = validate.titleCheck(title);
        String resContent = validate.contentCheck(content);
        // 유효하지 않을시 redirect
        if(!resTitle.equals("success")){
            String redirectPath = validate.handleResponseTitle(resTitle,rda,type,page,pageSize);
            return redirectPath;
        }

        if(!resContent.equals("success")){
            String redirectPath = validate.handleResponseContent(resContent,rda,type,page,pageSize);
            return redirectPath;
        }

        BoardDto boardDto = new BoardDto(type,writer,title,content);
        boardDto.setWriter(writer);
        try{
            boardService.writeBoard(boardDto);
            String uri="redirect:/board/list?type="+type+"&pageSize="+pageSize;
            return uri;
        }catch(Exception e){
            e.printStackTrace();
            rda.addFlashAttribute("error","게시물 등록에 실패했습니다.다시 시도해주세요.");
            String uri = "redirect:/board/write?type="+type+"&page="+page+"&pageSize="+pageSize;
            return uri;
        }
    }
    // *************************************************************************
    // 게시물 수정하기
    @GetMapping(value="/edit")
    public String boardEditGet(String type,Integer bno,String page,String pageSize,HttpSession session,RedirectAttributes rda,Model m){
        String id = getSessionId(session);
        if(id == null || "".equals(id)){
            rda.addFlashAttribute("error","로그인 해야 대나무숲에 접근할수 있습니다!");
        }
        if(type == null || "".equals(type)){
            rda.addFlashAttribute("error","페이지에 접근할수 없습니다.");
            return "redirect:/";
        }
        String nav = navCheck(type);
        String title=  titleCheck(type);
        try{
            // 게시물이 존재하는지 확인
            boolean answer = boardService.checkBoardByBno(bno);
            if(!answer){
                String uri = "redirect:/board/list?type="+type+"&page="+page+"&pageSize="+pageSize;
                rda.addFlashAttribute("error","게시물이 존재하지 않습니다.");
                return uri;
            }

            // 게시글 작성자가 아닐시 수정 거부
            BoardDto boardDto = boardService.readBoardByBno(bno);
            String writer = boardDto.getWriter();
            if(!(id.equals(writer))){
                String uri = "redirect:/board/list?type="+type+"&page="+page+"&pageSize="+pageSize;
                rda.addFlashAttribute("error","게시물은 작성자만 수정할수 있습니다.");
                return uri;
            }
            String nickname = userService.readUserById(id).getNickname();
            m.addAttribute("mode","edit");
            m.addAttribute("boardDto",boardDto);
            m.addAttribute("pageSize",pageSize);
            m.addAttribute("nav",nav);
            m.addAttribute("title",title);
            m.addAttribute("nickname",nickname);
            //throw new Exception("test");
            return "boardWrite";
        }catch(Exception e){
            e.printStackTrace();
            String uri = "redirect:/board/read?bno="+bno+"&page="+page+"&pageSize="+pageSize;
            rda.addFlashAttribute("error","페이지 방문에 실패.다시 시도해주세요.");
            return uri;
        }
    }
    @PostMapping(value="/edit")
    public String boardEditPost(BoardDto boardDto,Integer pageSize,Integer page,HttpSession session,RedirectAttributes rda,Model m){
        String writer = getSessionId(session);
        boardDto.setWriter(writer);
        String type = boardDto.getType();
        if(type == null || "".equals(type)){
            rda.addFlashAttribute("error","게시물 등록에 실패했습니다.");
            return "redirect:/";
        }
        try{
            boolean answer = boardService.checkBoardByBno(boardDto.getBno());
            if(!answer){
                String uri = "redirect:/board/list?type="+type+"&page="+page+"&pageSize="+pageSize;
                rda.addFlashAttribute("error","게시물이 존재하지 않습니다.");
                return uri;
            }
            // 게시글 작성자와 수정자가 일치하지 않을시 거부한다.
            BoardDto boardDtoServer = boardService.readBoardByBno(boardDto.getBno());

            if(!(boardDtoServer.getWriter().equals(boardDto.getWriter()))){
                rda.addFlashAttribute("error","게시물은 작성자만 수정할수 있습니다.");
                String uri = "redirect:/board/list?type="+type+"&page="+page+"&pageSize="+pageSize;
                return uri;
            }
            boardService.updateBoard(boardDto);
            Integer bno = boardDto.getBno();
            String uri = "redirect:/board/read?bno="+bno+"&page="+page+"&pageSize="+pageSize+"&type="+type;
            return uri;
        }catch (Exception e){
            e.printStackTrace();
            m.addAttribute("error","게시물 수정에 실패했습니다.");
            m.addAttribute("mode","edit");
            m.addAttribute("boardDto",boardDto);
            m.addAttribute("page",1);
            m.addAttribute("pageSize",pageSize);
            return "boardWrite";
        }
    }

    private String navCheck(String type) {
         if(type.equals("silver" ) || type.equals("english") || type.equals("secretary") || type.equals("tour") || type.equals("china") || type.equals("japan") ||
             type.equals("watching") || type.equals("multi") || type.equals("webtoon") || type.equals("vr")){
                return "silver";
         }else if(type.equals("auditorium")){
                return "auditorium";
         }else if(type.equals("disaster") || type.equals("virtue") || type.equals("architecture") || type.equals("safety") || type.equals("inner") || type.equals("city")){
                return "virtue";
         }else if(type.equals("person") || type.equals("industry") || type.equals("software") || type.equals("jewelry") || type.equals("ceramic") || type.equals("human")){
                return "person";
         }else if(type.equals("kinggod") || type.equals("kinggodgod") || type.equals("car") || type.equals("money")){
                return "kinggod";
         }else if(type.equals("library")){
             return "library";
         }else if(type.equals("engineering") || type.equals("computer") || type.equals("mecha") || type.equals("broadcast") || type.equals("information") || type.equals("air")){
             return "engineering";
         }else if(type.equals("welfare")){
             return "welfare";
         }else if(type.equals("formative") || type.equals("digital") || type.equals("influencer") || type.equals("beauty")){
             return "formative";
         }else if(type.equals("playground")){
             return "playground";
         }else if(type.equals("yenji") || type.equals("gym") || type.equals("foodcourt") || type.equals("stationery") || type.equals("printer")){
             return "yenji";
         }
         return "error";
    }
    private String titleCheck(String type) {
        if (type.equals("silver")) return "은봉관";
        if (type.equals("english")) return "비즈니스영어과";
        if (type.equals("secretary")) return "스마트경영비서학과";
        if (type.equals("tour")) return "관광서비스경영학과";
        if (type.equals("china")) return "비즈니스중국어학과";
        if (type.equals("japan")) return "비즈니스일본어과";
        if (type.equals("money")) return "세무회계학과";
        if (type.equals("watching")) return "시각디자인과";
        if (type.equals("multi")) return "멀티미디어디자인학과";
        if (type.equals("webtoon")) return "웹툰만화학과";
        if (type.equals("vr")) return "게임&VR콘텐츠디자인학과";

        if (type.equals("auditorium")) return "교회";

        if (type.equals("virtue")) return "덕관";
        if (type.equals("architecture")) return "건축학과";
        if (type.equals("city")) return "도시기반시설공학과";
        if(type.equals("disaster")) return "스마트건설방재학과";
        if (type.equals("inner")) return "실내건축과";

        if (type.equals("person")) return "인관";
        if (type.equals("industry")) return "산업경영공학과";
        if (type.equals("software")) return "컴퓨터소프트웨어학과";
        if (type.equals("jewelry")) return "주얼리디자인학과";
        if (type.equals("ceramic")) return "리빙세라믹디자인학과";
        if (type.equals("human")) return "사회복지학과";

        if (type.equals("kinggod")) return "제1공학관";
        if (type.equals("kinggodgod")) return "기계공학과";
        if (type.equals("car")) return "기계자동차공학과";

        if (type.equals("library")) return "도서관";

        if (type.equals("engineering")) return "제2공학관";
        if (type.equals("computer")) return "컴퓨터전자공학과";
        if (type.equals("mecha")) return "메카트로닉스공학과";
        if (type.equals("broadcast")) return "방송영상미디어학과";
        if (type.equals("information")) return "정보통신공학과";
        if (type.equals("air")) return "글로벌항공서비스학과";

        if (type.equals("welfare")) return "학생행복스퀘어";

        if (type.equals("formative")) return "조형관";
        if (type.equals("digital")) return "디지털산업디자인학과";
        if (type.equals("influencer")) return "방송연예과";
        if (type.equals("beauty")) return "방송헤어미용예술학과";

        if (type.equals("playground")) return "운동장";

        if (type.equals("yenji")) return "연지스퀘어";
        if (type.equals("gym")) return "헬스장";
        if (type.equals("stationery")) return "문구점";
        if (type.equals("printer")) return "프린터";
        if(type.equals("foodcourt")) return "학생식당";

        return "error";
    }
    private String getSessionId(HttpSession session){
        String id = (String)session.getAttribute("id");
        return id;
    }
}