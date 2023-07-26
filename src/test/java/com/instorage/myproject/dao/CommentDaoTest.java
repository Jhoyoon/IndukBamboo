//package com.instorage.myproject.dao;
//
//
//import com.instorage.myproject.domain.CommentDto;
//import org.junit.*;
//import org.junit.runner.*;
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.test.context.*;
//import org.springframework.test.context.junit4.*;
//
//import java.util.*;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class CommentDaoTest {
//    @Autowired
//    CommentDao commentDao;
//
//    @Test
//    public void count() throws Exception {
//        commentDao.deleteAllCommentByBno(1);
//        assertTrue(commentDao.countCommentByBno(1)==0);
//    }
//
//    @Test
//    public void delete() throws Exception {
//        commentDao.deleteAllCommentByBno(1);
//        CommentDto commentDto = new CommentDto(1, 0, "comment", "asdf");
//        assertTrue(commentDao.insertComment(commentDto)==1);
//        assertTrue(commentDao.countCommentByBno(1)==1);
//    }
//
//    @Test
//    public void insert() throws Exception {
//        commentDao.deleteAllCommentByBno(1);
//        CommentDto commentDto = new CommentDto(1, 0, "comment", "asdf");
//        assertTrue(commentDao.insertComment(commentDto)==1);
//        assertTrue(commentDao.countCommentByBno(1)==1);
//
//        commentDto = new CommentDto(1, 0, "comment", "asdf");
//        assertTrue(commentDao.insertComment(commentDto)==1);
//        assertTrue(commentDao.countCommentByBno(1)==2);
//    }
//
//    @Test
//    public void selectAll() throws Exception {
//        commentDao.deleteAllCommentByBno(1);
//        CommentDto commentDto = new CommentDto(1, 0, "comment", "asdf");
//        assertTrue(commentDao.insertComment(commentDto)==1);
//        assertTrue(commentDao.countCommentByBno(1)==1);
//
//        List<CommentDto> list = commentDao.selectAllCommentByBnoToList(1);
//        assertTrue(list.size()==1);
//
//        commentDto = new CommentDto(1, 0, "comment", "asdf");
//        assertTrue(commentDao.insertComment(commentDto)==1);
//        assertTrue(commentDao.countCommentByBno(1)==2);
//
//        list = commentDao.selectAllCommentByBnoToList(1);
//        assertTrue(list.size()==2);
//    }
//
//    @Test
//    public void select() throws Exception {
//        commentDao.deleteAllCommentByBno(1);
//        CommentDto commentDto = new CommentDto(1, 0, "comment", "asdf");
//        assertTrue(commentDao.insertComment(commentDto)==1);
//        assertTrue(commentDao.countCommentByBno(1)==1);
//
//        List<CommentDto> list = commentDao.selectAllCommentByBnoToList(1);
//        String comment = list.get(0).getComment();
//        String commenter = list.get(0).getCommenter();
//        assertTrue(comment.equals(commentDto.getComment()));
//        assertTrue(commenter.equals(commentDto.getCommenter()));
//    }
//
//    @Test
//    public void update() throws Exception {
//        commentDao.deleteAllCommentByBno(1);
//        CommentDto commentDto = new CommentDto(1, 0, "comment", "asdf");
//        assertTrue(commentDao.insertComment(commentDto)==1);
//        assertTrue(commentDao.countCommentByBno(1)==1);
//
//        List<CommentDto> list = commentDao.selectAllCommentByBnoToList(1);
//        commentDto.setCno(list.get(0).getCno());
//        commentDto.setComment("comment2");
//        assertTrue(commentDao.updateComment(commentDto)==1);
//
//        list = commentDao.selectAllCommentByBnoToList(1);
//        String comment = list.get(0).getComment();
//        String commenter = list.get(0).getCommenter();
//        assertTrue(comment.equals(commentDto.getComment()));
//        assertTrue(commenter.equals(commentDto.getCommenter()));
//    }
//}