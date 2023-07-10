package com.instorage.myproject.service;

import com.instorage.myproject.dao.BoardDao;
import com.instorage.myproject.domain.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BoardService {
    @Autowired
    BoardDao boardDao;
    // 게시물 개수 반환
    public int count(){
        return boardDao.count();
    }
    // 게시물 삭제.성공시 1 실패시 다른 숫자
    public int remove(Integer bno,String writer){
        return boardDao.delete(bno,writer);
    }
    // 모든 게시물 삭제.삭제된 게시물 개수 반환
    public int removeAll(){
        return boardDao.deleteAll();
    }
    // 게시물 작성.성고하면 1 실패하면 그 외 숫자
    public int write(BoardDto boardDto){
        return boardDao.insert(boardDto);
    }
    // 모든 게시물을 list 형태로 가져온다
    public List<BoardDto> getList(){
        return boardDao.selectAll();
    }
    // 게시물을 bno로 읽는다.객체 하나를 가져옴
    public BoardDto read(Integer bno){
        return boardDao.select(bno);
    }
    // 페이지를 가져온다.offset pagesize를 amp으로 받고 객체를 list로 반환한다.
    public List<BoardDto> getPage(Map map){
        return boardDao.selectPage(map);
    }
    
    // 게시물을 업데이트 한다.성공시 1 그 외 실패
    public int update(BoardDto boardDto){
        return boardDao.update(boardDto);
    }
    public int increaseView(Integer bno){
        return boardDao.increaseViewCnt(bno);
    }
}
