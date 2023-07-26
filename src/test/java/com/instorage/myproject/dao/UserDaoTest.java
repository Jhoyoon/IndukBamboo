//package com.instorage.myproject.dao;
//
//import com.instorage.myproject.domain.UserDto;
//import junit.framework.TestCase;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class UserDaoTest extends TestCase {
//    @Autowired
//    UserDao userdao;
//    @Test
//    public void testInsertUser() throws Exception{
//        int rowCnt=userdao.deleteAllUser();
//        assertTrue(rowCnt == 1);
//        UserDto user = new UserDto("id","pwd","nickname");
//        rowCnt=userdao.insertUser(user);
//        assertTrue(rowCnt == 1);
//        rowCnt=userdao.deleteAllUser();
//        assertTrue(rowCnt == 1);
//        userdao.deleteAllUser();
//    }
//@Test
//    public void testDeleteUser() throws Exception{
//        System.out.println(userdao.deleteAllUser());
//    }
//    @Test
//    public void testSelectUser()throws Exception{
//        userdao.deleteAllUser();
//        UserDto userDto = new UserDto("id","pwd","nickname");
//        int rowCnt = userdao.insertUser(userDto);
//        assertTrue(rowCnt == 1);
//        UserDto userDto2 = userdao.selectUserById(userDto.getId());
//        assertTrue(userDto.equals(userDto2));
//        userdao.deleteAllUser();
//    }
//    @Test
//    public void testCheckId()throws Exception{
//        userdao.deleteAllUser();
//        UserDto userDto = new UserDto("id","pwd","nickname");
//        int rowCnt = userdao.insertUser(userDto);
//        assertTrue(rowCnt==1);
//        boolean check = userdao.checkUserById(userDto.getId());
//        assertTrue(check);
//        boolean check2 = userdao.checkUserById("dd");
//        assertTrue(!check2);
//        userdao.deleteAllUser();
//    }
//    @Test
//    public void testDelete()throws Exception{
//        userdao.deleteAllUser();
//        UserDto userDto = new UserDto("id","pwd","nickname");
//        int rowCnt = userdao.insertUser(userDto);
//        assertTrue(rowCnt==1);
//        rowCnt = userdao.deleteUserById(userDto.getId());
//        assertTrue(rowCnt == 1);
//    }
//    @Test
//    public void testUpdate()throws Exception{
//        userdao.deleteAllUser();
//        UserDto userDto = new UserDto("id","pwd","nickname");
//        int rowCnt = userdao.insertUser(userDto);
//        assertTrue(rowCnt==1);
//        UserDto userDto2 = new UserDto("id","pwd2","nickname2");
//        rowCnt = userdao.updateUser(userDto2);
//        assertTrue(rowCnt == 1);
//        UserDto userDto3 = userdao.selectUserById(userDto.getId());
//        assertTrue(userDto3.getId().equals(userDto.getId()));
//        assertTrue(userDto3.getPwd().equals(userDto2.getPwd()));
//    }
//    @Test
//    @Transactional
//    public void testSelectUserByNickname()throws Exception{
//        userdao.deleteAllUser();
//        UserDto userDto = new UserDto("id","pwd","nickname");
//        int rowCnt =userdao.insertUser(userDto);
//        assertTrue(rowCnt == 1);
//        UserDto userDto2 =userdao.selectUserByNickname("nickname");
//        assertTrue(userDto.equals(userDto2));
//    }
//}