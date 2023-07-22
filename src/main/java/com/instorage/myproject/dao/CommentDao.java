package com.instorage.myproject.dao;

import com.instorage.myproject.domain.CommentDto;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CommentDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.instorage.myproject.commentMapper.";


    // 글 번호를 통해서 댓글이 몇개인지 개수를 반환한다.
    public int countCommentByBno(Integer bno) throws Exception {
        return session.selectOne(namespace+"countCommentByBno", bno);
    } // T selectOne(String statement)

    // 글 번호를 가지고 해당 글의 댓글을 전부 삭제한다.
    public int deleteAllCommentByBno(Integer bno) {
        return session.delete(namespace+"deleteAllCommentByBno", bno);
    } // int delete(String statement)

    // 댓글 번화와 작성자를 가지고 특정 댓글 한개를 삭제한다.
    public int deleteCommentByCnoAndCommenter(Integer cno, String commenter) throws Exception {
        Map map = new HashMap();
        map.put("cno", cno);
        map.put("commenter", commenter);
        return session.delete(namespace+"deleteCommentByCnoAndCommenter", map);
    } // int delete(String statement, Object parameter)

    // commentDto를 통해서 댓글 하나를 작성한다.
    public int insertComment(CommentDto dto) throws Exception {
        return session.insert(namespace+"insertComment", dto);
    } // int insert(String statement, Object parameter)

    // 글 번호를 통해서 해당 글에 달려있는 모든 댓글들을 list 형태로 반환한다
    public List<CommentDto> selectAllCommentByBnoToList(Integer bno) throws Exception {
        return session.selectList(namespace+"selectAllCommentByBnoToList", bno);
    } // List<E> selectList(String statement)

    // 댓글 번호를 통해서 하나의 댓글을 CommentDto로 반환한다.
    public CommentDto selectCommentByCno(Integer cno) throws Exception {
        return session.selectOne(namespace + "selectCommentByCno", cno);
    } // T selectOne(String statement, Object parameter)

    // CommentDto를 통해서 댓글 하나를 업데이트 한다.
    public int updateComment(CommentDto dto) throws Exception {
        return session.update(namespace+"updateComment", dto);
    } // int update(String statement, Object parameter)
}