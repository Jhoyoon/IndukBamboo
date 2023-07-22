package com.instorage.myproject.service;

import com.instorage.myproject.dao.*;
import com.instorage.myproject.domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class CommentService{
    // 인스턴스 변수 field에 직접 주입하는거 보다는 생성자를 통해서 주입해주는게 좋다.
    // 실수로 빈 등록을 안 했을시 알수있다.
    BoardDao boardDao;

    CommentDao commentDao;

    @Autowired
    public CommentService(CommentDao commentDao, BoardDao boardDao) {
        this.commentDao = commentDao;
        this.boardDao = boardDao;
    }


    public int getCount(Integer bno) throws Exception {
        return commentDao.count(bno);
    }

    // board의 댓글 개수를 하나 줄이고 삭제해야 한다.그렇기에 transactional을 검
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer cno, Integer bno, String commenter) throws Exception {
        int rowCnt = boardDao.updateCommentCntByBnoAndNum(bno, -1);
        System.out.println("updateCommentCnt - rowCnt = " + rowCnt);
//        throw new Exception("test");
        rowCnt = commentDao.delete(cno, commenter);
        System.out.println("rowCnt = " + rowCnt);
        return rowCnt;
    }
//    @Transactional(rollbackFor = Exception.class)
//    public int remove(Integer cno, Integer bno, String commenter) throws Exception {
//        int rowCnt = boardDao.updateCommentCnt(bno, -1);
//        rowCnt = commentDao.delete(cno, commenter);
//        if (rowCnt != 1) {
//            throw new Exception("delete failed");
//        }
//        return rowCnt;
//    }


    @Transactional(rollbackFor = Exception.class)
    public int write(CommentDto commentDto) throws Exception {
        boardDao.updateCommentCntByBnoAndNum(commentDto.getBno(), 1);
                //throw new Exception("test");
        return commentDao.insert(commentDto);
    }


    public List<CommentDto> getList(Integer bno) throws Exception {
//        throw new Exception("test");
        return commentDao.selectAll(bno);
    }


    public CommentDto read(Integer cno) throws Exception {
        return commentDao.select(cno);
    }


    public int modify(CommentDto commentDto) throws Exception {
        return commentDao.update(commentDto);
    }
}