package com.instorage.myproject.dao;

import com.instorage.myproject.domain.CommentDto;

import java.util.List;

public interface CommentDao {
    // 글 번호를 통해서 댓글이 몇개인지 개수를 반환한다.
    int countCommentByBno(Integer bno) throws Exception // T selectOne(String statement)
    ;

    // 글 번호를 가지고 해당 글의 댓글을 전부 삭제한다.
    int deleteAllCommentByBno(Integer bno) // int delete(String statement)
    ;

    // 댓글 번화와 작성자를 가지고 특정 댓글 한개를 삭제한다.
    int deleteCommentByCnoAndCommenter(Integer cno, String commenter) throws Exception // int delete(String statement, Object parameter)
    ;

    // commentDto를 통해서 댓글 하나를 작성한다.
    int insertComment(CommentDto dto) throws Exception // int insert(String statement, Object parameter)
    ;

    // 글 번호를 통해서 해당 글에 달려있는 모든 댓글들을 list 형태로 반환한다
    List<CommentDto> selectAllCommentByBnoToList(Integer bno) throws Exception // List<E> selectList(String statement)
    ;

    // 댓글 번호를 통해서 하나의 댓글을 CommentDto로 반환한다.
    CommentDto selectCommentByCno(Integer cno) throws Exception // T selectOne(String statement, Object parameter)
    ;

    // CommentDto를 통해서 댓글 하나를 업데이트 한다.
    int updateComment(CommentDto dto) throws Exception // int update(String statement, Object parameter)
    ;

    int deletedByCno(Integer cno) throws Exception;

    int plusReplyCntByPcno(Integer pcno, Integer num) throws Exception;
}
