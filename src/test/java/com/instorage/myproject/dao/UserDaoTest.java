package com.instorage.myproject.dao;

import com.instorage.myproject.domain.UserDto;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class UserDaoTest extends TestCase {
    @Autowired
    UserDao userdao;
    @Test
    public void testInsertUser() throws Exception{
        int rowCnt=userdao.deleteUser();
        assertTrue(rowCnt == 1);
        UserDto user = new UserDto("id","pwd","nickname", LocalDate.now());
        rowCnt=userdao.insertUser(user);
        assertTrue(rowCnt == 1);
        rowCnt=userdao.deleteUser();
        assertTrue(rowCnt == 1);
    }
@Test
    public void testDeleteUser() {
        System.out.println(userdao.deleteUser());
    }
}