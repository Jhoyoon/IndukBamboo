package com.instorage.myproject.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.instorage.myproject.dao.UserDao;
import com.instorage.myproject.domain.UserDto;
import com.instorage.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public String registerUser(UserDto user) throws Exception{
        // id가 이미 존재할시
        boolean checkId =userDao.checkUserById(user.getId());
        if(checkId) return "duplicateId";
        // nickname이 이미 존재할시
        boolean checkNickname = userDao.checkUserByNickname(user.getNickname());
        if(checkNickname) return "duplicateNickname";

        int rowCnt = userDao.insertUser(user);
        if(rowCnt == 1) return "success";
        else return "fail";

    }
    @Override
    public boolean checkUserById(String id) throws Exception{
        boolean checkId =userDao.checkUserById(id);
        return checkId;
    }
    @Override
    public boolean checkUserByNickname(String nickname) throws Exception{
        boolean checkNickname = userDao.checkUserByNickname(nickname);
        return checkNickname;
    }
    @Override
    public String loginCheck(String id, String pwd) throws Exception{
        boolean idCheck = userDao.checkUserById(id);
        if(!idCheck) return "NonexistentID";
        String dbPwd = userDao.selectUserById(id).getPwd();
        BCrypt.Verifyer verifyer = BCrypt.verifyer();
        BCrypt.Result result = verifyer.verify(pwd.toCharArray(),dbPwd);
        if(!(result.verified)) return "MismatchedPassword";
        return "success";
    }
    @Override
    public int removeAllUser() throws Exception{
        return userDao.deleteAllUser();
    }
    @Override
    public int removeUserById(String id) throws Exception{
        return userDao.deleteUserById(id);
    }
    @Override
    public int updateUser(UserDto userDto) throws Exception{
        return userDao.updateUser(userDto);
    }
    @Override
    public UserDto readUserById(String id) throws Exception{
        return userDao.selectUserById(id);
    }
    @Override
    public UserDto readUserByNickname(String nickname) throws Exception{
        return userDao.selectUserByNickname(nickname);
    }
    @Override
    public int countAllUser() throws Exception{
        return userDao.countAllUser();
    }
    @Override
    public int totalBoardCount() throws Exception{
        return userDao.totalBoardCount();
    }
}
