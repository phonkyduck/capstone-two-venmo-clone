package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/id-for-{username}")
    public int getId(@PathVariable String username){
        return userDao.findIdByUsername(username);
    }

    @GetMapping(path = "/find_user-{username}")
    public User findUser(@PathVariable String username){
        return userDao.findByUsername(username);
    }

    @PostMapping
    public boolean createUser(@RequestBody String username, @RequestBody String password){
        return userDao.create(username, password);
    }
}
