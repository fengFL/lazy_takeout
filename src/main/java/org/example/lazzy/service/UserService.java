package org.example.lazzy.service;

import org.example.lazzy.pojo.User;
import org.springframework.stereotype.Service;


public interface UserService {
    /**
     * login service
     */
    User selectByPhone(String phone);


}
