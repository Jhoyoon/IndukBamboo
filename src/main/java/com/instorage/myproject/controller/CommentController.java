package com.instorage.myproject.controller;

import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.CommentDto;
import com.instorage.myproject.service.CommentService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;
    // create
    @PostMapping(value="/comments")
    // @RequestBody = json 문자열을 자바 객체로 바꿔준다.
    // @ResponseBody = 자바 객체를 json 문자열로 바꿔준다.
    public ResponseEntity<Map<String,String>> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) {
        String commenter = (String)session.getAttribute("id");
        if(commenter == null || "".equals(commenter)){
            Map<String, String> response = new HashMap<>();
            response.put("res","로그인 해야 댓글을 작성할수 있습니다.회원가입 고고");
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        // 특수문자를 바꿔준다.스크립트 공격 방지
        String convertedComment = StringEscapeUtils.escapeHtml4(dto.getComment());
        dto.setCommenter(commenter);
        dto.setBno(bno);
        dto.setComment(convertedComment);
        try {
//            throw new Exception("test");
            if(commentService.writeComment(dto)!=1)
                throw new Exception("Write failed.");
            Map<String, String> response = new HashMap<>();
            response.put("res","댓글 작성 성공");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("res","댓글 작성 실패.다시 시도해 주세요.");
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
    }
    // delete
    @DeleteMapping(value="/comments/{cno}") // cno는 param으로 넘어오는게 아니라 uri의 일부러 넘어온다.그렇기에 @PageVariable 써야함
    public ResponseEntity<Map<String,String>> delete(@PathVariable Integer cno, Integer bno,HttpSession session){
        String id = (String)session.getAttribute("id");
        Map response = new HashMap();
        try{
            CommentDto commentDto = commentService.readCommentByCno(cno);
            if(commentDto.getReply_cnt() != 0){
                commentService.deletedByCno(cno);
                response.put("res","댓글 삭제에 성공했습니다.");
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .body(response);
            }
            // 대댓글이 없을 경우 그냥 삭제한다
            int rowCnt = commentService.removeCommentByCnoAndBnoAndCommenter(cno, bno, commenter);
            if (rowCnt != 1) throw new Exception("delete failed");
            response.put("res", "댓글 삭제에 성공했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("res","댓글 삭제에 실패했습니다.다시 시도해 주세요.");
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .body(response);
    }

    // update
    @PatchMapping(value="/comments/{cno}")
    public ResponseEntity<Map<String,String>> update(@PathVariable Integer cno,@RequestBody Map<String, String> commentMap,HttpSession session){
        String id = (String)session.getAttribute("id");
        Map response = new HashMap();
        // 스크립트 공격을 방지해준다.
        String originalComment = commentMap.get("comment");
        String convertedComment = originalComment.replace("\n","<br>");
        try{
            CommentDto commentDto = commentService.readCommentByCno(cno);
            if(!(commentDto.getCommenter().equals(id))){
                response.put("res","댓글은 작성자만 수정할수 있습니다.");
                return ResponseEntity.badRequest()
                        .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .body(response);
            }
            commentDto.setComment(convertedComment);
            commentService.updateComment(commentDto);
            response.put("res","댓글 수정에 성공했습니다.");
            //throw new Exception("test");
        }catch(Exception e){
            e.printStackTrace();
            response.put("res","댓글 수정에 실패했습니다.다시 시도해 주세요.");
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(response);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .body(response);
    }
    // read 해당 글에 있는 모든 댓글을 전부 다 가져온다
    @GetMapping(value="/comments") // bno=555 이런식으로 정보가 들어오겠지.그니까 매개변수로 bno를 받자
    public ResponseEntity<List<CommentDto>> read(Integer bno){
        List<CommentDto> list= new ArrayList<>();
        try{
            //throw new Exception("test");
            list = commentService.getCommentByBnoToList(bno);
        }catch(Exception e){
            e.printStackTrace();
            CommentDto commentDto = new CommentDto(bno,"댓글을 가져오는데 실패했습니다.새로고침 해주세요.","error");
            list.add(commentDto);
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .body(list);
        }
        // list에 상태코드까지 같이 보내준다.
        //entity에 list랑 상태 코드를 같이 담아서 보내는거임
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .body(list);
    }
    private String getSessionId(HttpSession session){
        String id = (String)session.getAttribute("id");
        return id;
    }
}

