package com.instorage.myproject.service;

import com.instorage.myproject.domain.CommentDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
    int countCommentByBno(Integer bno) throws Exception;

    // board의 댓글 개수를 하나 줄이고 삭제해야 한다.그렇기에 transactional을 검
    @Transactional(rollbackFor = Exception.class)
    int removeCommentByCnoAndBnoAndCommenter(Integer cno, Integer bno, String commenter) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    int writeComment(CommentDto commentDto) throws Exception;

    List<CommentDto> getCommentByBnoToList(Integer bno) throws Exception;

    CommentDto readCommentByCno(Integer cno) throws Exception;

    int updateComment(CommentDto commentDto) throws Exception;

    //
    @Transactional(rollbackFor = Exception.class)
    int deletedByCno(Integer cno) throws Exception;
}
