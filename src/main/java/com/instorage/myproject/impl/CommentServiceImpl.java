package com.instorage.myproject.impl;

import com.instorage.myproject.dao.*;
import com.instorage.myproject.domain.*;
import com.instorage.myproject.service.CommentService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    // 인스턴스 변수 field에 직접 주입하는거 보다는 생성자를 통해서 주입해주는게 좋다.
    // 실수로 빈 등록을 안 했을시 알수있다.
    BoardDao boardDao;

    CommentDao commentDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, BoardDao boardDao) {
        this.commentDao = commentDao;
        this.boardDao = boardDao;
    }


    @Override
    public int countCommentByBno(Integer bno) throws Exception {
        return commentDao.countCommentByBno(bno);
    }

    // board의 댓글 개수를 하나 줄이고 삭제해야 한다.그렇기에 transactional을 검
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeCommentByCnoAndBnoAndCommenter(Integer cno, Integer bno, String commenter) throws Exception {
        int rowCnt = boardDao.updateCommentCntByBnoAndNum(bno, -1);

//        throw new Exception("test");
        rowCnt = commentDao.deleteCommentByCnoAndCommenter(cno, commenter);

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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int writeComment(CommentDto commentDto) throws Exception {
        boardDao.updateCommentCntByBnoAndNum(commentDto.getBno(), 1);
        // 조상의 댓글 개수를 하나 올려준다 대댓글 작성시
        if(commentDto.getPcno() != null){
            commentDao.plusReplyCntByPcno(commentDto.getPcno(),1);
        }
                //throw new Exception("test");
        return commentDao.insertComment(commentDto);
    }


    @Override
    public List<CommentDto> getCommentByBnoToList(Integer bno) throws Exception {
//        throw new Exception("test");
        return commentDao.selectAllCommentByBnoToList(bno);
    }


    @Override
    public CommentDto readCommentByCno(Integer cno) throws Exception {
        return commentDao.selectCommentByCno(cno);
    }


    @Override
    public int updateComment(CommentDto commentDto) throws Exception {
        return commentDao.updateComment(commentDto);
    }
    //
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletedByCno(Integer cno) throws Exception{
        CommentDto commentDto = commentDao.selectCommentByCno(cno);
        boardDao.updateCommentCntByBnoAndNum(commentDto.getBno(), -1);
        if(commentDto != null){
            commentDao.plusReplyCntByPcno(commentDto.getPcno(),-1);
        }
        // 여기서 deleted를 0으로 바꿔준다!!
        return commentDao.deletedSetZeroByCno(cno);
    }
}