package com.instorage.myproject.service;

import com.instorage.myproject.dao.BoardDao;
import com.instorage.myproject.dao.CommentDao;
import com.instorage.myproject.domain.BoardDto;
import com.instorage.myproject.domain.CommentDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentDao commentDao;
    @Autowired
    BoardDao boardDao;

    @Test
    public void remove() throws Exception {
        boardDao.deleteAll();
        //다 지우고 하나 넣고 selectall로 불러온뒤 하나만 뽑아내면 bno를 알아낼수 있다.
        BoardDto boardDto = new BoardDto("hello", "hello", "asdf");
        assertTrue(boardDao.insert(boardDto) == 1);
        Integer bno = boardDao.selectAll().get(0).getBno();
        System.out.println("bno = " + bno);

        commentDao.deleteAll(bno);
        CommentDto commentDto = new CommentDto(bno,0,"hi","qwer");

        assertTrue(boardDao.select(bno).getComment_cnt() == 0);
        assertTrue(commentService.write(commentDto)==1);
        System.out.println(boardDao.select(bno).getComment_cnt());
        assertTrue(boardDao.select(bno).getComment_cnt() == 1);

        Integer cno = commentDao.selectAll(bno).get(0).getCno();

        // 일부러 예외를 발생시키고 Tx가 취소되는지 확인해야.
        int rowCnt = commentService.remove(cno, bno, commentDto.getCommenter());
        assertTrue(rowCnt==1);
        assertTrue(boardDao.select(bno).getComment_cnt() == 0);
    }

    @Test
    public void write() throws  Exception {
        boardDao.deleteAll();

        BoardDto boardDto = new BoardDto("hello", "hello", "asdf");
        assertTrue(boardDao.insert(boardDto) == 1);
        Integer bno = boardDao.selectAll().get(0).getBno();
        System.out.println("bno = " + bno);

        commentDao.deleteAll(bno);
        CommentDto commentDto = new CommentDto(bno,0,"hi","qwer");

        assertTrue(boardDao.select(bno).getComment_cnt() == 0);
        assertTrue(commentService.write(commentDto)==1);

        Integer cno = commentDao.selectAll(bno).get(0).getCno();
        assertTrue(boardDao.select(bno).getComment_cnt() == 1);
    }

}