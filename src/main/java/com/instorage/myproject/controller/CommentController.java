package com.instorage.myproject.controller;

import com.instorage.myproject.domain.CommentDto;
import com.instorage.myproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;
    // create
    @PostMapping(value="/comments")
    // @RequestBody = json 문자열을 자바 객체로 바꿔준다.
    // @ResponseBody = 자바 객체를 json 문자열로 바꿔준다.
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) {
//        String commenter = (String)session.getAttribute("id");
        String commenter = "wjddbs9350";
        dto.setCommenter(commenter);
        dto.setBno(bno);

        try {
            if(commentService.write(dto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<String>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }
    // delete
    @DeleteMapping(value="/comments/{cno}") // cno는 param으로 넘어오는게 아니라 uri의 일부러 넘어온다.그렇기에 @PageVariable 써야함
    public HttpEntity<String> delete(@PathVariable Integer cno, Integer bno,HttpSession session){
        //String commenter = (String)session.getAttribute("id");
        String commenter = "commenter";
        try{
            int rowCnt = commentService.remove(cno,bno,commenter);

            if(rowCnt!=1) throw new Exception("delete failed");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("delete failed",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("delete ok",HttpStatus.OK);
    }
    // update
    @PatchMapping(value="/comments/{cno}")
    public ResponseEntity<String> update(@PathVariable Integer cno,@RequestBody CommentDto commentDto){
        commentDto.setCno(cno);
        try{
            commentService.modify(commentDto);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("update failed",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("update sucess",HttpStatus.OK);
    }
    // read 해당 글에 있는 모든 댓글을 전부 다 가져온다
    @GetMapping(value="/comments") // bno=555 이런식으로 정보가 들어오겠지.그니까 매개변수로 bno를 받자
    public ResponseEntity<List<CommentDto>> read(Integer bno){
        List<CommentDto> list=null;
        try{
            list = commentService.getList(bno);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST);
        }
        // list에 상태코드까지 같이 보내준다.
        // entity에 list랑 상태 코드를 같이 담아서 보내는거임
        return new ResponseEntity<List<CommentDto>>(list,HttpStatus.OK) ;
    }
}
