package com.instorage.myproject.dao;

import com.instorage.myproject.domain.BoardDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDao {
    @Autowired
    DataSource ds;
    @Autowired
    private SqlSession session;
    public void auto() {
        int rowCnt = 0;
        String sql = "ALTER TABLE board AUTO_INCREMENT = 1";

        try (  // try-with-resources - since jdk7
               Connection conn = ds.getConnection();
               PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.execute();
      } catch (Exception e) {
          e.printStackTrace();
        }
    }
    private static String namespace = "com.instorage.myproject.boardMapper.";
    // 행 개수세기
    public int count(){
        return session.selectOne(namespace+"count");
    }
    // 게시물 전부 삭제하기
    public int deleteAll(){
        return session.delete(namespace+"deleteAll");
    }

    // 특정 게시물 삭제하기
    public int delete(Integer bno,String writer ){
        Map map = new HashMap<>();
        map.put("bno",bno);
        map.put("writer",writer);
        return session.delete(namespace+"delete",map);
    }
    // 게시물 등록하기
    public int insert(BoardDto boardDto){
        return session.insert(namespace+"insert",boardDto);
    }
    // 게시물 번호로 게시물 하나 가져오기
    public BoardDto select(Integer bno){
        return session.selectOne(namespace+"select",bno);
    }
    // 게시물 업데이트
    public int update(BoardDto boardDto){
        return session.update(namespace+"update",boardDto);
    }
    // 모든 게시물 리스트로 반환하기
    public List<BoardDto> selectAll(){
        return session.selectList(namespace+"selectAll");
    }
    // 같은 페이지에 들어갈 board들 반환받기.map에는 page 정보가 담겨있다.
    public List<BoardDto> selectPage(Map map){
        return session.selectList(namespace+"selectPage",map);
    }
    // 조회수 올리기
    public int increaseViewCnt(Integer bno){
        return session.update(namespace+"increaseViewCnt",bno);
    }
}
