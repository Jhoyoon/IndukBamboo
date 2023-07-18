package com.instorage.myproject.service;

import com.instorage.myproject.dao.UserDao;
import com.instorage.myproject.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.sql.SQLException;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public int registerUser (UserDto user){
        boolean checkId =userDao.checkUserById(user.getId());
        if(checkId) return 2;
        boolean checkNickname = userDao.checkUserByNickname(user.getNickname());
        if(checkNickname) return 3;
        try{
        int rowCnt = userDao.insertUser(user);
        return rowCnt;
        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            return 4;
        }catch(BadSqlGrammarException e){
            e.printStackTrace();
            return 5;
        }catch(CannotGetJdbcConnectionException e) {
            e.printStackTrace();
            return 6;
        }
    }
    public int loginCheck(String id,String pwd){
        boolean idCheck = userDao.checkUserById(id);
        if(!idCheck) return 1;
        String dbPwd = userDao.selectUserById(id).getPwd();
        if(pwd.equals(dbPwd)) return 0;
        else return 2;
    }
    public int removeAll(){
        return userDao.deleteAll();
    }
    public int remove(String id){
        return userDao.deleteUserById(id);
    }
    public int update(UserDto userDto){
        return userDao.updateUser(userDto);
    }
    public UserDto read(String id){
        return userDao.selectUserById(id);
    }
}
