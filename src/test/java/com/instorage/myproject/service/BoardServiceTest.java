package com.instorage.myproject.service;

import com.instorage.myproject.domain.BoardDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardServiceTest {
    @Autowired
    BoardService boardService;
    @Test
    public void testCount()throws Exception{
        boardService.removeAllBoard();
        int rowCnt = boardService.countAllBoard();
        assertTrue(rowCnt == 0);
        BoardDto boardDto = new BoardDto("yenji","writer","title","content");
        rowCnt = boardService.writeBoard(boardDto);
        assertTrue(rowCnt == 1);
        boardDto = boardService.readBoardByBno(1);
        assertTrue(boardDto.getTitle().equals("title"));
        boardService.removeAllBoard();
    }
    @Test
    public void testUpdate()throws Exception{
        boardService.removeAllBoard();
        BoardDto boardDto1 = new BoardDto("yenji","writer","title","content");
        boardDto1.setBno(1);
        int rowCnt = boardService.writeBoard(boardDto1);
        assertTrue(rowCnt == 1);
        BoardDto boardDto2 = new BoardDto("yenji","writer","title2","content2");
        boardDto2.setBno(1);
        rowCnt = boardService.updateBoard(boardDto2);
        assertTrue(rowCnt == 1);
        BoardDto boardDto3 = boardService.readBoardByBno(1);
        assertTrue(boardDto3.getTitle().equals("title2"));
        boardService.removeAllBoard();
    }
    @Test
    public void testDate()throws Exception{
        for(int i=0;i<=200;i++){
        BoardDto boardDto = new BoardDto("yenji","wjddbs9350","title"+i,"content");
        boardService.writeBoard(boardDto);
        }
    }
}