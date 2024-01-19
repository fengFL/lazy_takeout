package org.example.lazzy.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.lazzy.common.BaseContext;
import org.example.lazzy.common.Result;
import org.example.lazzy.pojo.User;
import org.example.lazzy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     *  user login
     */
    @PostMapping("/login")
    public Result<User> login(HttpServletRequest request, @RequestBody User user){
        String phone = user.getPhone();
        log.info("user phone is ----> {}", phone);
        // call userService to check or save the user info, and return user info
        User userInfo = userService.selectByPhone(user.getPhone());
        // set user id into session
        request.getSession().setAttribute("user", userInfo.getId());
        return Result.success(userInfo);
    }

    /**
     *  user login
     */
    @PostMapping("/loginout")
    public Result<String> login(HttpServletRequest request){
        // get user ID
        Long userId = BaseContext.getCurrentId();

        request.getSession().removeAttribute("user");

        return Result.success("logout successful");
    }

}
