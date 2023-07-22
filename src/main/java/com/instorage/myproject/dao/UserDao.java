package com.instorage.myproject.dao;

import com.instorage.myproject.domain.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private SqlSession session;
    private String namespace = "com.instorage.myproject.userMapper.";
    // 유저 객체를 db에 넣는다.
    public int insertUser(UserDto user) throws Exception {
        return session.insert(namespace+"insertUser",user);
    }
    // 모든 user들을 삭제한다.
    public int deleteAllUser() throws Exception{
        return session.delete(namespace+"deleteAllUser");
    }
    // id로 유저 객체 하나를 반환한다.
    public UserDto selectUserById(String id) throws Exception{
        return session.selectOne(namespace+"selectUserById",id);
    }
    public UserDto selectUserByNickname(String nickname) throws Exception{
        return session.selectOne(namespace+"selectUserByNickname",nickname);
    }
    // 해당 id가 존재하는지 boolean값을 반환한다.
    public boolean checkUserById(String id) throws Exception{
        return session.selectOne(namespace+"checkUserById",id);
    }
    public boolean checkUserByNickname(String nickname )throws Exception{
        return session.selectOne(namespace+"checkUserByNickname",nickname);
    }
    // id를 통해서 user 객체 하나를 삭제한다.
    public int deleteUserById(String id) throws Exception{
        return session.delete(namespace+"deleteUserById",id);
    }
    // userDto 객체를 통해서 user를 업데이트 해준다.
    public int updateUser(UserDto userDto) throws Exception{
        return session.update(namespace+"updateUser",userDto);
    }
}
