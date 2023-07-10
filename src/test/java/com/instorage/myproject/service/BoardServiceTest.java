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
    public void testCount(){
        boardService.removeAll();
        int rowCnt = boardService.count();
        assertTrue(rowCnt == 0);
        BoardDto boardDto = new BoardDto("writer","title","content");
        rowCnt = boardService.write(boardDto);
        assertTrue(rowCnt == 1);
        boardDto = boardService.read(1);
        assertTrue(boardDto.getTitle().equals("title"));
        boardService.removeAll();
    }
    @Test
    public void testUpdate(){
        boardService.removeAll();
        BoardDto boardDto1 = new BoardDto("writer","title","content");
        boardDto1.setBno(1);
        int rowCnt = boardService.write(boardDto1);
        assertTrue(rowCnt == 1);
        BoardDto boardDto2 = new BoardDto("writer","title2","content2");
        boardDto2.setBno(1);
        rowCnt = boardService.update(boardDto2);
        assertTrue(rowCnt == 1);
        BoardDto boardDto3 = boardService.read(1);
        assertTrue(boardDto3.getTitle().equals("title2"));
        boardService.removeAll();
    }
    @Test
    public void testDate(){
        for(int i=0;i<=200;i++){
        BoardDto boardDto = new BoardDto("wjddbs9350","title"+i,"content");
        boardService.write(boardDto);
        }
    }
}