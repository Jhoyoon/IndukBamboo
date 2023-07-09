package com.instorage.myproject.dao;

import com.instorage.myproject.domain.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private SqlSession session;
    private String namespace = "com.instorage.myproject.userMapper.";
    // 유저 객체를 db에 넣는다.
    public int insert(UserDto user) throws DuplicateKeyException{
        return session.insert(namespace+"insert",user);
    }
    // 모든 user들을 삭제한다.
    public int deleteAll(){
        return session.delete(namespace+"deleteAll");
    }
    // id로 유저 객체 하나를 반환한다.
    public UserDto select(String id){
        return session.selectOne(namespace+"select",id);
    }
    // 해당 id가 존재하는지 boolean값을 반환한다.
    public boolean checkId(String id){
        return session.selectOne(namespace+"checkId",id);
    }
    // id를 통해서 user 객체 하나를 삭제한다.
    public int delete(String id){
        return session.delete(namespace+"delete",id);
    }
    // userDto 객체를 통해서 user를 업데이트 해준다.
    public int update(UserDto userDto){
        return session.update(namespace+"update",userDto);
    }
}
