package com.instorage.myproject.service;

import com.instorage.myproject.domain.UserDto;

public interface UserService {
    String registerUser(UserDto user) throws Exception;

    boolean checkUserById(String id) throws Exception;

    boolean checkUserByNickname(String nickname) throws Exception;

    String loginCheck(String id, String pwd) throws Exception;

    int removeAllUser() throws Exception;

    int removeUserById(String id) throws Exception;

    int updateUser(UserDto userDto) throws Exception;

    UserDto readUserById(String id) throws Exception;

    UserDto readUserByNickname(String nickname) throws Exception;
    int countAllUser() throws Exception;
    int totalBoardCount() throws Exception;
}
