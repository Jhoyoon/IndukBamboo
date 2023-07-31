package com.instorage.myproject.service;

import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.SearchCondition;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface BoardService {
    // 게시물 개수 반환
    int countAllBoard() throws Exception;

    // 게시물 삭제.성공시 1 실패시 다른 숫자
    @Transactional(rollbackFor = Exception.class)
    int removeBoardByBnoAndWriter(Integer bno, String writer) throws Exception;

    // 모든 게시물 삭제.삭제된 게시물 개수 반환
    int removeAllBoard() throws Exception;

    // 게시물 작성.성고하면 1 실패하면 그 외 숫자
    @Transactional(rollbackFor = Exception.class)
    int writeBoard(BoardDto boardDto) throws Exception;

    // 모든 게시물을 list 형태로 가져온다
    List<BoardDto> getBoardToList() throws Exception;

    // 게시물을 bno로 읽는다.객체 하나를 가져옴
    BoardDto readBoardByBno(Integer bno) throws Exception;

    // 페이지를 가져온다.offset pagesize를 amp으로 받고 객체를 list로 반환한다.
    List<BoardDto> getPageByMap(Map map) throws Exception;

    // 게시물을 업데이트 한다.성공시 1 그 외 실패
    int updateBoard(BoardDto boardDto) throws Exception;

    int increaseViewCntByBno(Integer bno) throws Exception// 그럼 어떻게 반환할까 이건 디코로 하자 컴온
    ;

    List<BoardDto> selectSearchPage(SearchCondition search) throws Exception;

    int countSearchPage(SearchCondition search) throws Exception;

    boolean checkBoardByBno(Integer bno) throws Exception;
}
