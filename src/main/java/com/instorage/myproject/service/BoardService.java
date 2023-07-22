package com.instorage.myproject.service;

import com.instorage.myproject.dao.BoardDao;
import com.instorage.myproject.dao.CommentDao;
import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BoardService {
    @Autowired
    BoardDao boardDao;
    @Autowired
    CommentDao commentDao;
    // 게시물 개수 반환
    public int countAllBoard()throws Exception{
        return boardDao.countAllBoard();
    }
    // 게시물 삭제.성공시 1 실패시 다른 숫자
    @Transactional(rollbackFor = Exception.class)
    public int removeBoardByBnoAndWriter(Integer bno,String writer){
        int rowCnt = boardDao.deleteBoardByBnoAndWriter(bno,writer);
        commentDao.deleteAll(bno);
        return rowCnt;
    }
    // 모든 게시물 삭제.삭제된 게시물 개수 반환
    public int removeAllBoard()throws Exception{
        return boardDao.deleteAllBoard();
    }
    // 게시물 작성.성고하면 1 실패하면 그 외 숫자
    public int writeBoard(BoardDto boardDto)throws Exception{
        return boardDao.insertBoard(boardDto);
    }
    // 모든 게시물을 list 형태로 가져온다
    public List<BoardDto> getBoardToList()throws Exception{
        return boardDao.selectAllBoard();
    }
    // 게시물을 bno로 읽는다.객체 하나를 가져옴
    public BoardDto readBoardByBno(Integer bno)throws Exception{
        return boardDao.selectBoardByBno(bno);
    }
    // 페이지를 가져온다.offset pagesize를 amp으로 받고 객체를 list로 반환한다.
    public List<BoardDto> getPageByMap(Map map)throws Exception{
        return boardDao.selectPageByMap(map);
    }
    
    // 게시물을 업데이트 한다.성공시 1 그 외 실패
    public int updateBoard(BoardDto boardDto)throws Exception{
        return boardDao.updateBoard(boardDto);
    }
    public int increaseViewCntByBno(Integer bno)throws Exception{
        return boardDao.increaseViewCntByBno(bno);
    }
    public List<BoardDto> selectSearchPage(SearchCondition search)throws Exception{
        return boardDao.selectSearchPage(search);
    }
    public int countSearchPage(SearchCondition search)throws Exception{
        return boardDao.countSearchPage(search);
    }
    public boolean checkBoardByBno(Integer bno) throws Exception{
        return boardDao.checkBoardByBno(bno);
    }
}
