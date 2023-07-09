package com.instorage.myproject.dao;

import com.instorage.myproject.domain.UserDto;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class UserDaoTest extends TestCase {
    @Autowired
    UserDao userdao;
    @Test
    public void testInsertUser() throws Exception{
        int rowCnt=userdao.deleteAll();
        assertTrue(rowCnt == 1);
        UserDto user = new UserDto("id","pwd","nickname");
        rowCnt=userdao.insert(user);
        assertTrue(rowCnt == 1);
        rowCnt=userdao.deleteAll();
        assertTrue(rowCnt == 1);
        userdao.deleteAll();
    }
@Test
    public void testDeleteUser() {
        System.out.println(userdao.deleteAll());
    }
    @Test
    public void testSelectUser(){
        userdao.deleteAll();
        UserDto userDto = new UserDto("id","pwd","nickname");
        int rowCnt = userdao.insert(userDto);
        assertTrue(rowCnt == 1);
        UserDto userDto2 = userdao.select(userDto.getId());
        assertTrue(userDto.equals(userDto2));
        userdao.deleteAll();
    }
    @Test
    public void testCheckId(){
        userdao.deleteAll();
        UserDto userDto = new UserDto("id","pwd","nickname");
        int rowCnt = userdao.insert(userDto);
        assertTrue(rowCnt==1);
        boolean check = userdao.checkId(userDto.getId());
        assertTrue(check);
        boolean check2 = userdao.checkId("dd");
        assertTrue(!check2);
        userdao.deleteAll();
    }
    @Test
    public void testDelete(){
        userdao.deleteAll();
        UserDto userDto = new UserDto("id","pwd","nickname");
        int rowCnt = userdao.insert(userDto);
        assertTrue(rowCnt==1);
        rowCnt = userdao.delete(userDto.getId());
        assertTrue(rowCnt == 1);
    }
    @Test
    public void testUpdate(){
        userdao.deleteAll();
        UserDto userDto = new UserDto("id","pwd","nickname");
        int rowCnt = userdao.insert(userDto);
        assertTrue(rowCnt==1);
        UserDto userDto2 = new UserDto("id","pwd2","nickname2");
        rowCnt = userdao.update(userDto2);
        assertTrue(rowCnt == 1);
        UserDto userDto3 = userdao.select(userDto.getId());
        assertTrue(userDto3.getId().equals(userDto.getId()));
        assertTrue(userDto3.getPwd().equals(userDto2.getPwd()));

    }
}