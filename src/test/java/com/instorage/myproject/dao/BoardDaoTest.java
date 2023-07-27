package com.instorage.myproject.dao;

import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.CommentDto;
import com.instorage.myproject.domain.SearchCondition;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardDaoTest extends TestCase {
    @Autowired
    BoardDao boardDao;
    @Autowired
    CommentDao commentDao;

    @Test
    public void testData() throws Exception{
        for(int i=1;i<=1000;i++){
            BoardDto boardDto = new BoardDto("silver","wjddbs9350",i+"title","content");
            boardDao.insertBoard(boardDto);
        }
    }
    @Test
    public void testCount() throws Exception{
        boardDao.deleteAllBoard();
        int rowCnt=boardDao.countAllBoard();
        assertTrue(rowCnt == 0);
    }
    @Test
    public void testdeleteAllBoard()throws Exception{
        boardDao.deleteAllBoard();
        int rowCnt = boardDao.deleteAllBoard();
        assertTrue(rowCnt==0);
    }
    @Test
    public void testDelete()throws Exception{
        int rowCnt=boardDao.deleteBoardByBnoAndWriter(3,"writer");
        System.out.println(rowCnt);
        assertTrue(rowCnt == 1);
        rowCnt=boardDao.deleteAllBoard();
        assertTrue(rowCnt == 1);
    }
    @Test
    public void testInsert()throws Exception{
        boardDao.deleteAllBoard();
        BoardDto boardDto = new BoardDto("yenji","writer","title","content");
        int rowCnt=boardDao.insertBoard(boardDto);
        assertTrue(rowCnt==1);
        rowCnt=boardDao.deleteAllBoard();
        assertTrue(rowCnt==1);
    }
    @Test
    public void testSelect()throws Exception{
        boardDao.deleteAllBoard();
        boardDao.insertBoard(new BoardDto("yenji","writer","title","content"));
        BoardDto boardDto=boardDao.selectBoardByBno(1);
        assertTrue(boardDto != null);
        assertTrue(boardDto.getTitle().equals("title"));
    }
    @Test
    public void testAuto(){
        boardDao.auto();
    }
    @Test
    public void testUpdate()throws Exception{
        boardDao.deleteAllBoard();
        boardDao.insertBoard(new BoardDto("yenji","writer","title","content"));
        BoardDto boardDto = new BoardDto("yenji","writer","title2","content2");
        boardDto.setBno(1);
        int rowCnt = boardDao.updateBoard(boardDto);
        System.out.println(rowCnt);
        assertTrue(rowCnt == 1);
        BoardDto boardDto2 = boardDao.selectBoardByBno(1);
        assertTrue(boardDto2.getTitle().equals("title2"));
        boardDao.deleteAllBoard();
    }
    @Test
    public void testSelectAll()throws Exception{
        boardDao.deleteAllBoard();
        boardDao.insertBoard(new BoardDto("yenji","writer","title","content"));
        boardDao.insertBoard(new BoardDto("yenji","writer","title2","content2"));
        List<BoardDto> list =boardDao.selectAllBoard();
        BoardDto boardDto1 = list.get(0);
        BoardDto boardDto2 = list.get(1);
        assertTrue(boardDto1.getTitle().equals("title2"));
        assertTrue(boardDto2.getTitle().equals("title"));
        boardDao.deleteAllBoard();
    }
    @Test
    public void testTest()throws Exception{
        boardDao.deleteAllBoard();
    }
    @Test
    public void testSelectPage()throws Exception{
        boardDao.deleteAllBoard();
        for(int i=0;i<=9;i++){
            boardDao.insertBoard(new BoardDto("yenji","writer","title"+i,"content"));
        }
        Map map = new HashMap();
        map.put("offset",0);
        map.put("pageSize",10);
        List<BoardDto> list = boardDao.selectPageByMap(map);
        for(int i=0;i<=9;i++){
            assertTrue(list.get(i).getTitle().equals("title"+i));
        }
        boardDao.deleteAllBoard();
    }
    @Test
    public void testIncreaseCnt()throws Exception{
        boardDao.deleteAllBoard();
        boardDao.insertBoard(new BoardDto("yenji","writer","title","content"));
        BoardDto boardDto = boardDao.selectBoardByBno(1);
        boardDto.setComment_cnt(0);
        int rowCnt = boardDao.increaseViewCntByBno(1);
        assertTrue(rowCnt == 1);
        boardDao.deleteAllBoard();
    }
    @Test
    public void testSelectSearchPage()throws Exception{
        boardDao.deleteAllBoard();
        SearchCondition search = new SearchCondition("t","title",1,10);
        boardDao.insertBoard(new BoardDto("yenji","writer","title","content"));
        List<BoardDto> list= boardDao.selectSearchPage(search);
        assertTrue(list.size() == 1);
        boardDao.insertBoard(new BoardDto("yenji","writer","title","content"));
        list = boardDao.selectSearchPage(search);
        assertTrue(list.size() == 2);
    }
    @Test
    public void testCountSearch()throws Exception{
        boardDao.deleteAllBoard();
        SearchCondition search = new SearchCondition("t","title",1,10);
        boardDao.insertBoard(new BoardDto("yenji","writer","title","content"));
        int count = boardDao.countSearchPage(search);
        assertTrue(count == 1);
        boardDao.insertBoard(new BoardDto("yenji","writer","title2","content"));
        count = boardDao.countSearchPage(search);
        assertTrue(count == 2);
        boardDao.insertBoard(new BoardDto("yenji","writer","title28dd","content"));
        count = boardDao.countSearchPage(search);
        assertTrue(count == 3);
        boardDao.insertBoard(new BoardDto("yenji","writer","titl","content"));
        count = boardDao.countSearchPage(search);
        assertTrue(count == 3);
        boardDao.deleteAllBoard();
    }
    @Test
    public void testUpdateCommentCnt() throws Exception{
        boardDao.deleteAllBoard();
        boardDao.insertBoard(new BoardDto("yenji","writer","title","content"));
        int count = commentDao.countCommentByBno(1);
        assertTrue(count == 0);
        int bno = boardDao.selectAllBoard().get(0).getBno();
        commentDao.deleteAllCommentByBno(bno);
        int rowCnt=commentDao.insertComment(new CommentDto(bno,"content","commenter"));
        assertTrue(rowCnt == 1);
        rowCnt=boardDao.updateCommentCntByBnoAndNum(bno,1);
        assertTrue(rowCnt == 1);
        assertTrue(boardDao.selectAllBoard().get(0).getComment_cnt() == 1);
    }
}