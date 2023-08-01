package com.instorage.myproject.impl;

import com.instorage.myproject.dao.BoardDao;
import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDaoImpl implements BoardDao {
    @Autowired
    DataSource ds;
    @Autowired
    private SqlSession session;
    private static String namespace = "com.instorage.myproject.boardMapper.";
    // 행 개수세기
    @Override
    public int countAllBoard()throws Exception{
        return session.selectOne(namespace+"countAllBoard");
    }
    // 게시물 전부 삭제하기
    @Override
    public int deleteAllBoard()throws Exception{
        return session.delete(namespace+"deleteAllBoard");
    }

    // 특정 게시물 삭제하기
    @Override
    public int deleteBoardByBnoAndWriter(Integer bno, String writer){
        Map map = new HashMap<>();
        map.put("bno",bno);
        map.put("writer",writer);
        return session.delete(namespace+"deleteBoardByBnoAndWriter",map);
    }
    // 게시물 등록하기
    @Override
    public int insertBoard(BoardDto boardDto)throws Exception{
        return session.insert(namespace+"insertBoard",boardDto);
    }
    // 게시물 번호로 게시물 하나 가져오기
    @Override
    public BoardDto selectBoardByBno(Integer bno)throws Exception{
        return session.selectOne(namespace+"selectBoardByBno",bno);
    }
    // 게시물 업데이트
    @Override
    public int updateBoard(BoardDto boardDto)throws Exception{
        return session.update(namespace+"updateBoard",boardDto);
    }
    // 모든 게시물 리스트로 반환하기
    @Override
    public List<BoardDto> selectAllBoard()throws Exception{
        return session.selectList(namespace+"selectAllBoard");
    }
    // 같은 페이지에 들어갈 board들 반환받기.map에는 page 정보가 담겨있다.
    @Override
    public List<BoardDto> selectPageByMap(Map map)throws Exception{
        return session.selectList(namespace+"selectPageByMap",map);
    }
    // 조회수 올리기
    @Override
    public int increaseViewCntByBno(Integer bno)throws Exception{
        return session.update(namespace+"increaseViewCntByBno",bno);
    }
    @Override
    public List<BoardDto> selectSearchPage(SearchCondition search)throws Exception{
        return session.selectList(namespace+"selectSearchPage",search);
    }
    @Override
    public int countSearchPage(SearchCondition search)throws Exception{
        return session.selectOne(namespace+"countSearchPage",search);
    }

    @Override
    public int updateCommentCntByBnoAndNum(Integer bno, int num)throws Exception {
        Map map = new HashMap();
        map.put("bno",bno);
        map.put("num",num);
        return session.update(namespace+"updateCommentCntByBnoAndNum",map);
    }
    // 게시물이 실제로 존재하는지 확인한다.
    @Override
    public boolean checkBoardByBno(Integer bno) throws Exception{
        return session.selectOne(namespace+"checkBoardByBno",bno);
    }
}
