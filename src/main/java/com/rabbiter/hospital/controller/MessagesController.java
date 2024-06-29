package com.rabbiter.hospital.controller;


import com.rabbiter.hospital.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Messages")
public class MessagesController {

    @GetMapping("getUserInfo")
    public User getUserInfo(){
        User user = new User();

        return  user;
    }
}
