package com.instorage.myproject.service;

import com.instorage.myproject.dao.UserDao;
import com.instorage.myproject.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public int registerUser (UserDto user) throws Exception{
        int rowCnt = userDao.insertUser(user);
        return rowCnt;
    }

}
