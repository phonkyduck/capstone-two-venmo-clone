package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private JdbcUserDao userDao;

    @GetMapping
    public List<User> getAll(){
        return userDao.findAll();
    }

    @GetMapping
    public int getId(String username){
        return userDao.findIdByUsername(username);
    }

    @GetMapping
    public User findUser(String username){
        return userDao.findByUsername(username);
    }

    @PostMapping
    public boolean createUser(String username, String password){
        return userDao.create(username, password);
    }
}
