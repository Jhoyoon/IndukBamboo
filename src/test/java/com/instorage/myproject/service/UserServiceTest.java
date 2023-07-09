package com.instorage.myproject.service;

import com.instorage.myproject.dao.UserDao;
import com.instorage.myproject.domain.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    @Test
    public void registerUser() {
    }

    @Test
    public void loginCheck() {
        userDao.deleteAll();
        UserDto userDto = new UserDto("id","pwd","nickname");
        int rowCnt = userService.registerUser(userDto);
        assertTrue(rowCnt == 1);
        int check = userService.loginCheck("id","pwd");
        System.out.println(check);
        assertTrue(check == 0);
        userDao.deleteAll();
    }
}