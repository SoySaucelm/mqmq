package com.mq.letter.controller;

import com.mq.letter.entity.TUser;
import com.mq.letter.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private TUserMapper userMapper;

    @RequestMapping("auth")
    public Object authLogin(){
        List<TUser> tUsers = userMapper.selectAll();
        System.out.println(tUsers);
        return tUsers;
    }
}
