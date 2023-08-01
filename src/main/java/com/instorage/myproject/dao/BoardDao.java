package com.instorage.myproject.dao;

import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardDao {
    // 행 개수세기
    int countAllBoard() throws Exception;

    // 게시물 전부 삭제하기
    int deleteAllBoard() throws Exception;

    // 특정 게시물 삭제하기
    int deleteBoardByBnoAndWriter(Integer bno, String writer);

    // 게시물 등록하기
    int insertBoard(BoardDto boardDto) throws Exception;

    // 게시물 번호로 게시물 하나 가져오기
    BoardDto selectBoardByBno(Integer bno) throws Exception;

    // 게시물 업데이트
    int updateBoard(BoardDto boardDto) throws Exception;

    // 모든 게시물 리스트로 반환하기
    List<BoardDto> selectAllBoard() throws Exception;

    // 같은 페이지에 들어갈 board들 반환받기.map에는 page 정보가 담겨있다.
    List<BoardDto> selectPageByMap(Map map) throws Exception;

    // 조회수 올리기
    int increaseViewCntByBno(Integer bno) throws Exception;

    List<BoardDto> selectSearchPage(SearchCondition search) throws Exception;

    int countSearchPage(SearchCondition search) throws Exception;

    int updateCommentCntByBnoAndNum(Integer bno, int num) throws Exception;

    // 게시물이 실제로 존재하는지 확인한다.
    boolean checkBoardByBno(Integer bno) throws Exception;
}
