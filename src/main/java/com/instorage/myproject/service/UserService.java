package com.instorage.myproject.service;

import com.instorage.myproject.dao.UserDao;
import com.instorage.myproject.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public int registerUser (UserDto user) throws DuplicateKeyException{
        int rowCnt = userDao.insert(user);
        return rowCnt;
    }
    public int loginCheck(String id,String pwd){
        boolean idCheck = userDao.checkId(id);
        if(!idCheck) return 1;
        String dbPwd = userDao.select(id).getPwd();
        System.out.println(dbPwd);

        if(pwd.equals(dbPwd)) return 0;
        else return 2;
    }
    public int removeAll(){
        return userDao.deleteAll();
    }
    public int remove(String id){
        return userDao.delete(id);
    }
    public int update(UserDto userDto){
        return userDao.update(userDto);
    }
    public UserDto read(String id){
        return userDao.select(id);
    }
}
