package com.instorage.myproject.dao;

import com.instorage.myproject.domain.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private SqlSession session;
    private String namespace = "com.instorage.myproject.userMapper.";
    public int insertUser(UserDto user) throws Exception{
        return session.insert(namespace+"insertUser",user);
    }
    public int deleteUser(){
        return session.delete(namespace+"deleteAll");
    }
}
