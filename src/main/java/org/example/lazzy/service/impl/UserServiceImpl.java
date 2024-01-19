package org.example.lazzy.service.impl;

import org.example.lazzy.common.CustomException;
import org.example.lazzy.mapper.UserMapper;
import org.example.lazzy.pojo.User;
import org.example.lazzy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     *  query user info by phone
     * @param phone
     * @return
     */
    @Override
    public User selectByPhone(String phone) {
        // determine whether user exists in database
        User user = userMapper.selectByPhone(phone);
        if(user == null) {
            // user does not exist
            // add the user to the database
            user = new User();
            user.setPhone(phone);
            user.setId(System.currentTimeMillis());
            userMapper.add(user);
        }
        // user already exist
        return user;
    }
}
