package com.instorage.myproject.impl;

import com.instorage.myproject.dao.BoardDao;
import com.instorage.myproject.dao.CommentDao;
import com.instorage.myproject.dao.UserDao;
import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.SearchCondition;
import com.instorage.myproject.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {

    BoardDao boardDao;

    CommentDao commentDao;

    UserDao userDao;
    @Autowired
    public BoardServiceImpl(CommentDao commentDao, BoardDao boardDao, UserDao userDao) {
        this.commentDao = commentDao;
        this.boardDao = boardDao;
        this.userDao = userDao;
    }

    // 게시물 개수 반환
    @Override
    public int countAllBoard()throws Exception{
        return boardDao.countAllBoard();
    }

    // 게시물 삭제.성공시 1 실패시 다른 숫자
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeBoardByBnoAndWriter(Integer bno, String writer) throws Exception{
        int rowCnt = boardDao.deleteBoardByBnoAndWriter(bno,writer);
        commentDao.deleteAllCommentByBno(bno);
        userDao.updateBoardCntById(writer,-1);
        return rowCnt;
    }
    // 모든 게시물 삭제.삭제된 게시물 개수 반환
    @Override
    public int removeAllBoard()throws Exception{
        return boardDao.deleteAllBoard();
    }
    // 게시물 작성.성고하면 1 실패하면 그 외 숫자
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int writeBoard(BoardDto boardDto)throws Exception{
        userDao.updateBoardCntById(boardDto.getWriter(),1);
        return boardDao.insertBoard(boardDto);
    }
    // 모든 게시물을 list 형태로 가져온다
    @Override
    public List<BoardDto> getBoardToList()throws Exception{
        return boardDao.selectAllBoard();
    }
    // 게시물을 bno로 읽는다.객체 하나를 가져옴
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BoardDto readBoardByBno(Integer bno)throws Exception{
        boardDao.increaseViewCntByBno(bno);
        return boardDao.selectBoardByBno(bno);
    }
    // 페이지를 가져온다.offset pagesize를 amp으로 받고 객체를 list로 반환한다.
    @Override
    public List<BoardDto> getPageByMap(Map map)throws Exception{
        return boardDao.selectPageByMap(map);
    }
    
    // 게시물을 업데이트 한다.성공시 1 그 외 실패
    @Override
    public int updateBoard(BoardDto boardDto)throws Exception{
        return boardDao.updateBoard(boardDto);
    }
    @Override
    public int increaseViewCntByBno(Integer bno)throws Exception{
        return boardDao.increaseViewCntByBno(bno);
    }// 그럼 어떻게 반환할까 이건 디코로 하자 컴온
    @Override
    public List<BoardDto> selectSearchPage(SearchCondition search)throws Exception{
        return boardDao.selectSearchPage(search);
    }
    @Override
    public int countSearchPage(SearchCondition search)throws Exception{
        return boardDao.countSearchPage(search);
    }
    @Override
    public boolean checkBoardByBno(Integer bno) throws Exception{
        return boardDao.checkBoardByBno(bno);
    }
}
