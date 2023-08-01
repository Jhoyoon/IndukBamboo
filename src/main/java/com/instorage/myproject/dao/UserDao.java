package com.instorage.myproject.dao;

import com.instorage.myproject.domain.UserDto;

public interface UserDao {
    // 유저 객체를 db에 넣는다.
    int insertUser(UserDto user) throws Exception;

    // 모든 user들을 삭제한다.
    int deleteAllUser() throws Exception;

    // id로 유저 객체 하나를 반환한다.
    UserDto selectUserById(String id) throws Exception;

    UserDto selectUserByNickname(String nickname) throws Exception;

    // 해당 id가 존재하는지 boolean값을 반환한다.
    boolean checkUserById(String id) throws Exception;

    boolean checkUserByNickname(String nickname) throws Exception;

    // id를 통해서 user 객체 하나를 삭제한다.
    int deleteUserById(String id) throws Exception;

    // userDto 객체를 통해서 user를 업데이트 해준다.
    int updateUser(UserDto userDto) throws Exception;

    int updateBoardCntById(String id, Integer num) throws Exception;

    int countAllUser() throws Exception;

    int totalBoardCount() throws Exception;
}
