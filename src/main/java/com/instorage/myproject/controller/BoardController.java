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
    public String boardList(SearchCondition sc, HttpServletRequest request,RedirectAttributes rda,Model m){
        if(sc.getPage() == null) sc.setPage(1);
        if(sc.getPageSize() == null) sc.setPageSize(30);
        if(sc.getType() == null || "".equals(sc.getType())){
            rda.addFlashAttribute("error","게시판에 접근할수 없습니다.");
            return "redirect:/";
        }
        // req에서 sessionid로 서버에서 세션을 찾는다.
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("id");

        if(id==null || "".equals(id)){
            rda.addFlashAttribute("error","로그인 해야 대나무숲에 접근할수 있습니다!");
            return "redirect:/";
        }
        String nav = navCheck(sc.getType());
        String title = titleCheck(sc.getType());
        try{
            int totalSize=boardService.countSearchPage(sc);
            PageHandler ph = new PageHandler(totalSize,sc);
            List<BoardDto> list =boardService.selectSearchPage(sc);
            String nickName=userService.readUserById(id).getNickname();
            System.out.println("size="+list.size());
            if(list.size() == 0) m.addAttribute("none","*게시물이 없습니다*");
            m.addAttribute("list",list);
            m.addAttribute("ph",ph);
            m.addAttribute("nav",nav);
            m.addAttribute("nickName",nickName);
            m.addAttribute("title",title);
            return "boardList";
        }catch(Exception e){
            e.printStackTrace();
            rda.addFlashAttribute("error","페이지 로드시 에러가 발생했습니다.다시 시도해주세요.");
            return "redirect:/";
        }
    }


    // ************************************************************************
    // 게시물 제거
    @GetMapping(value="/remove")
    public String boardRemove(String type,Integer bno, Integer page, Integer pageSize,HttpSession session,RedirectAttributes rda){
        String id =(String)session.getAttribute("id");
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
            boolean check = boardService.checkBoardByBno(bno);
            if(!check){
                uri = "redirect:/board/list?type="+type+"&page=" + page + "&pageSize=" + pageSize;
                rda.addFlashAttribute("error","게시물이 존재하지 않습니다.");
                return uri;
            }
            BoardDto boardDto = boardService.readBoardByBno(bno);
            String writer = boardDto.getWriter();
            if(!(writer.equals(id))){
                uri = "redirect:/board/list?type="+type+"&page=" + page + "&pageSize=" + pageSize;
                rda.addFlashAttribute("error","게시물은 작성자만 삭제할수 있습니다.");
                return uri;
            }
            int answer = boardService.removeBoardByBnoAndWriter(bno,id);
            if(answer==1){
                uri = "redirect:/board/list?type="+type+"&page=" + page + "&pageSize=" + pageSize ;
                rda.addFlashAttribute("error","삭제성공");
            }else{
                uri = "redirect:/board/read?type="+type+"&bno=" + bno + "&page=" + page + "&pageSize=" + pageSize;
                rda.addFlashAttribute("error","게시물은 작성자만 삭제할수 있습니다.");
            }
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
        String id = (String)session.getAttribute("id");
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
            boolean answer = boardService.checkBoardByBno(bno);
            if(!answer){
                String uri = "redirect:/board/list?page="+page+"&pageSize="+pageSize+"&nav="+nav;
                rda.addFlashAttribute("error","게시물이 존재하지 않습니다.");
                return uri;
            }
            BoardDto boardDto = boardService.readBoardByBno(bno);
            String originalContent = boardDto.getContent();
            String convertedContent = originalContent.replace("\n","<br>");
            boardDto.setContent(convertedContent);
            System.out.println("read= "+boardDto.getContent());
            boardService.increaseViewCntByBno(bno);
            String nickname = userService.readUserById(boardDto.getWriter()).getNickname();
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
        String id = (String)session.getAttribute("id");
        if(id == null || "".equals(id)){
            rda.addFlashAttribute("error","로그인 해야 대나무숲에 접근할수 있습니다!");
            return "redirect:/";
        }
        if(pageSize == null) pageSize = 10;
        if(type == null || "".equals(type)){
            rda.addFlashAttribute("error","페이지에 접근할수 없습니다.");
            return "redirect:/";
        }
        String nav =navCheck(type);
        String title = titleCheck(type);
        m.addAttribute("nav",nav);
        m.addAttribute("title",title);
        return "boardWrite";
    }
    //왜 boardDto로 한번에 받는게 안 되지...?edit에서는 됐는데
    @PostMapping(value="/write")
    public String boardWritePost(String type,String title,String content,Integer page,Integer pageSize,HttpSession session,RedirectAttributes rda,Model m){
        String writer = (String)session.getAttribute("id");
        if(type == null || "".equals(type)){
            rda.addFlashAttribute("error","게시물 등록에 실패했습니다.");
            return "redirect:/";
        }

        BoardDto boardDto = new BoardDto(type,writer,title,content);
        boardDto.setWriter(writer);
        if(title == null || "".equals(title)){
            rda.addFlashAttribute("error","제목을 입력해주세요!");
            String uri = "redirect:/board/write?type="+type+"&page="+page+"&pageSize="+pageSize;
            return uri;
        }
        if(content == null || "".equals(content)) {
            rda.addFlashAttribute("error", "내용을 입력해주세요!");
            String uri = "redirect:/board/write?type=" + type + "&page=" + page + "&pageSize=" + pageSize;
            return uri;
        }
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
        String id = (String)session.getAttribute("id");
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
            boolean answer = boardService.checkBoardByBno(bno);
            if(!answer){
                String uri = "redirect:/board/list?type="+type+"&page="+page+"&pageSize="+pageSize;
                rda.addFlashAttribute("error","게시물이 존재하지 않습니다.");
                return uri;
            }
            BoardDto boardDto = boardService.readBoardByBno(bno);

            String writer = boardDto.getWriter();
            if(!(id.equals(writer))){
                String uri = "redirect:/board/list?type="+type+"&page="+page+"&pageSize="+pageSize;
                rda.addFlashAttribute("error","게시물은 작성자만 수정할수 있습니다.");
                return uri;
            }

            m.addAttribute("mode","edit");
            m.addAttribute("boardDto",boardDto);
            m.addAttribute("pageSize",pageSize);
            m.addAttribute("nav",nav);
            m.addAttribute("title",title);
            //throw new Exception("test");
            return "boardWrite";
        }catch(Exception e){
            e.printStackTrace();
            String uri = "redirect:/board/read?bno="+bno+"&page="+page+"&pageSize="+pageSize;
            rda.addFlashAttribute("error","페이지 방문에 실패했습니다.다시 시도해주세요.");
            return uri;
        }
    }
    @PostMapping(value="/edit")
    public String boardEditPost(BoardDto boardDto,Integer pageSize,Integer page,HttpSession session,RedirectAttributes rda,Model m){
        String writer = (String)session.getAttribute("id");
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
    private String titleCheck(String type) {
        if (type.equals("silver")) return "은봉관";
        if (type.equals("english")) return "비즈니스영어과";
        if (type.equals("secretary")) return "비서학과";
        if (type.equals("tour")) return "관광서비스경영학과";
        if (type.equals("china")) return "비즈니스중국어과";
        if (type.equals("japan")) return "비즈니스일본어과";
        if (type.equals("money")) return "세무회계학과";
        if (type.equals("watching")) return "시각디자인과";
        if (type.equals("multi")) return "멀티미디어디자인학과";
        if (type.equals("webtoon")) return "웹툰만화창작학과";
        if (type.equals("vr")) return "게임&VR콘텐츠디자인학과";

        if (type.equals("auditorium")) return "교회";

        if (type.equals("virtue")) return "덕관";
        if (type.equals("architecture")) return "건축학과";
        if (type.equals("wood")) return "토목공학과";
        if (type.equals("safety")) return "건설안전공학과";
        if (type.equals("inner")) return "실내건축과";

        if (type.equals("person")) return "인관";
        if (type.equals("industry")) return "산업경영공학과";
        if (type.equals("software")) return "컴퓨터소프트웨어학과";
        if (type.equals("jewelry")) return "주얼리디자인학과";
        if (type.equals("ceramic")) return "리빙세라믹디자인학과";
        if (type.equals("human")) return "휴먼사회복지학과";

        if (type.equals("kinggod")) return "제1공학관";
        if (type.equals("kinggodgod")) return "융합기계공학과";
        if (type.equals("car")) return "기계자동차학과";

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
        if (type.equals("beauty")) return "방송뷰티과";
        if (type.equals("city")) return "도시디자인학과";

        if (type.equals("playground")) return "운동장";

        if (type.equals("yenji")) return "연지스퀘어";
        if (type.equals("gym")) return "헬스장";
        if (type.equals("stationery")) return "문구점";
        if (type.equals("printer")) return "프린터";
        if(type.equals("foodcourt")) return "학생식당";

        return "error";
    }
}
